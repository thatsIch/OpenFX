package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.prediction.intern.control.command.service.BinaryPredictionFileStorageService;
import de.thatsich.openfx.prediction.intern.control.entity.BinaryPrediction;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.security.InvalidParameterException;
import java.util.UUID;

public class TestBinaryClassificationCommand extends ACommand<BinaryPrediction>
{
	private final IImage image;
	private final int frameSize;
	private final IErrorGenerator errorGenerator;
	private final IFeatureExtractor featureExtractor;
	private final IBinaryClassification binaryClassification;

	// Injections
	@Inject
	private BinaryPredictionFileStorageService storage;

	@Inject
	private TestBinaryClassificationCommand(@Assisted IImage image, @Assisted int frameSize, @Assisted IErrorGenerator errorGenerator, @Assisted IFeatureExtractor featureExtractor, @Assisted IBinaryClassification binaryClassification)
	{
		this.image = image;
		this.frameSize = frameSize;
		this.errorGenerator = errorGenerator;
		this.featureExtractor = featureExtractor;
		this.binaryClassification = binaryClassification;
	}

	/*
	 * Extracts error and determines if a frame has an Error
	 * which will be placed in the second layer.
	 * You run through the prediction of the frame withError
	 * and multiple the prediction value (between 0 and 1)
	 * with 255 to get a decent color value. This will be placed
	 * into the third image layer.
	 */
	@Override
	protected BinaryPrediction call() throws Exception
	{
		final Mat original = this.image.getImageMat();
		final Mat modified = this.generateError(original);
		final Mat error = this.onlyError(original, modified);
		final Mat[][] withErrorSplit = this.splitImage(modified, this.frameSize);
		final Mat[][] onlyErrorSplit = this.splitImage(error, this.frameSize);
		final Mat[][] errorIndicationSplit = this.getErrorIndicationMat(onlyErrorSplit);
		final Mat errorIndicationMat = this.assembleImage(errorIndicationSplit, error.size());
		final MatOfFloat[][] featureVectorSplit = this.getFeatureVectorSplit(withErrorSplit, this.featureExtractor);
		final Mat[][] predictionMatSplit = this.predictError(featureVectorSplit, this.frameSize, this.binaryClassification);
		final Mat predictionMat = this.assembleImage(predictionMatSplit, error.size());
		this.log.info("Prepared Image Content.");

		final String strBinaryClassification = this.binaryClassification.getName();
		final String strFeatureExtractor = this.featureExtractor.getName();
		final int intFrameSize = this.frameSize;
		final String strErrorGenerator = this.errorGenerator.getName();
		final String strID = UUID.randomUUID().toString();
		this.log.info("Resolved FileName.");

		final BinaryPrediction prediction = new BinaryPrediction(modified, errorIndicationMat, predictionMat, strBinaryClassification, strFeatureExtractor, intFrameSize, strErrorGenerator, strID);
		this.log.info("Created Binary Prediction.");

		this.storage.create(prediction);
		this.log.info("Stored Prediction to FileSystem.");

		return prediction;
	}

	private Mat generateError(Mat image)
	{
		final Mat clone = image.clone();
		final Mat error = this.errorGenerator.generateError(clone);
		this.log.info("Generated Error.");

		return error;
	}

	private Mat onlyError(Mat withoutError, Mat withError)
	{
		Mat result = new Mat();
		Core.absdiff(withError, withoutError, result);
		this.log.info("Extracted only Error.");

		return result;
	}

	private Mat[][] splitImage(Mat image, int frameSize)
	{
		final Mat[][] result = Images.split(image, frameSize, frameSize);
		this.log.info("Split image.");

		return result;
	}

	private Mat[][] getErrorIndicationMat(Mat[][] onlyErrorSplit)
	{
		final int cols = onlyErrorSplit.length;
		final int rows = onlyErrorSplit[0].length;

		final Mat[][] errorIndicationMat = new Mat[cols][rows];

		for (int col = 0; col < onlyErrorSplit.length; col++)
		{
			for (int row = 0; row < onlyErrorSplit[col].length; row++)
			{
				errorIndicationMat[col][row] = this.getErrorIndicationMat(onlyErrorSplit[col][row]);
			}
		}

		return errorIndicationMat;
	}

	private Mat assembleImage(Mat[][] split, Size assembledSize)
	{
		final Mat assembly = Mat.zeros(assembledSize, CvType.CV_8UC1);

		for (int col = 0; col < split.length; col++)
		{
			for (int row = 0; row < split[col].length; row++)
			{
				final Mat splitPart = split[col][row];
				final int frameSize = splitPart.rows();
				final Mat roi = assembly.submat(frameSize * row, frameSize * (row + 1), frameSize * col, frameSize * (col + 1));
				splitPart.copyTo(roi);
			}
		}

		return assembly;
	}

	private MatOfFloat[][] getFeatureVectorSplit(Mat[][] withErrorSplit, IFeatureExtractor extractor)
	{
		final int cols = withErrorSplit.length;
		final int rows = withErrorSplit[0].length;

		final MatOfFloat[][] featureVectorSplit = new MatOfFloat[cols][rows];

		for (int col = 0; col < withErrorSplit.length; col++)
		{
			for (int row = 0; row < withErrorSplit[col].length; row++)
			{
				featureVectorSplit[col][row] = extractor.extractFeature(withErrorSplit[col][row]);
			}
		}

		return featureVectorSplit;
	}

	private Mat[][] predictError(MatOfFloat[][] featureVectorSplit, int predictionFrameSize, IBinaryClassification classification)
	{
		final int cols = featureVectorSplit.length;
		final int rows = featureVectorSplit[0].length;

		final Mat[][] predictionMatSplit = new Mat[cols][rows];

		for (int col = 0; col < featureVectorSplit.length; col++)
		{
			for (int row = 0; row < featureVectorSplit[col].length; row++)
			{
				final double prediction = classification.predict(featureVectorSplit[col][row].t());
				predictionMatSplit[col][row] = this.getPredictionMat(predictionFrameSize, prediction);
			}
		}

		return predictionMatSplit;
	}

	private Mat getErrorIndicationMat(Mat onlyErrorSplitPart)
	{
		if (Core.sumElems(onlyErrorSplitPart).val[0] != 0)
		{
			final Scalar value = new Scalar(255);
			return new Mat(onlyErrorSplitPart.size(), onlyErrorSplitPart.type(), value);
		}
		else
		{
			return Mat.zeros(onlyErrorSplitPart.size(), onlyErrorSplitPart.type());
		}
	}

	private Mat getPredictionMat(int matSize, double prediction)
	{
		if (prediction < 0 || 1 < prediction)
		{
			throw new InvalidParameterException("Prediction out of bound [0,1]: " + prediction);
		}

		final Scalar value = new Scalar(255 * prediction);
		final Mat predictionMat = new Mat(matSize, matSize, CvType.CV_8UC1, value);
		this.log.info("Created PreditionMat.");

		return predictionMat;
	}
}
