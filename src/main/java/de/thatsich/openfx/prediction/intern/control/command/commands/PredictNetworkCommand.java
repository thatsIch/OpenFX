package de.thatsich.openfx.prediction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.CreateErrorCommand;
import de.thatsich.openfx.errorgeneration.intern.control.entity.Error;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.network.api.control.entity.ITrainedNetwork;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.command.service.NetworkPredictionFileStorageService;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPredictionConfig;
import javafx.util.Pair;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PredictNetworkCommand extends ACommand<INetworkPrediction>
{
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	private final IImage image;
	private final ITrainedNetwork network;
	private final IErrorCommandProvider errorProvider;
	private final IErrorGenerator errorGenerator;

	// Injections
	@Inject
	private NetworkPredictionFileStorageService storage;

	@Inject
	private PredictNetworkCommand(@Assisted IImage image, @Assisted IErrorGenerator errorGenerator, @Assisted ITrainedNetwork network, IErrorCommandProvider errorProvider)
	{
		this.image = image;
		this.network = network;
		this.errorProvider = errorProvider;
		this.errorGenerator = errorGenerator;
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
	protected INetworkPrediction call() throws Exception
	{
		final CreateErrorCommand createErrorCommand = this.errorProvider.createCreateErrorCommand(this.image, this.errorGenerator, false, false, false);
		final IError createdError = createErrorCommand.call();

		final Mat modified = createdError.modifiedProperty().get();

		final IError[][] errorSplit = this.splitError(createdError);
		final Pair<String[][], Double[][]> predictionSplit = this.predictErrorSplit(errorSplit);
		this.log.info("Prepared Image Content.");

		final String creationTime = format.format(new Date());
		final String predictedClassName = this.network.predict(createdError).getKey();
		final String strID = UUID.randomUUID().toString();
		this.log.info("Resolved FileName.");

		final NetworkPredictionConfig config = new NetworkPredictionConfig(creationTime, predictedClassName, strID);
		this.log.info("Resolved config.");

		final INetworkPrediction prediction = new NetworkPrediction(config, modified, errorSplit, predictionSplit.getKey(), predictionSplit.getValue());
		this.log.info("Created Binary Prediction.");

		this.storage.create(prediction);
		this.log.info("Stored Prediction to FileSystem.");


		return prediction;
	}

	private IError[][] splitError(IError e)
	{
		final ErrorConfig config = e.getConfig();
		final Mat original = e.originalProperty().get();
		final Mat modified = e.modifiedProperty().get();
		final Mat error = e.errorProperty().get();

		final Mat[][] originalSplit = this.splitImage(original, 15);
		final Mat[][] modifiedSplit = this.splitImage(modified, 15);
		final Mat[][] errorSplit = this.splitImage(error, 15);

		final int cols = originalSplit.length;
		final int rows = originalSplit[0].length;

		final IError[][] errors = new Error[cols][rows];

		for (int col = 0; col < originalSplit.length; col++)
		{
			for (int row = 0; row < originalSplit[0].length; row++)
			{
				final Mat originalMat = originalSplit[col][row];
				final Mat modifiedMat = modifiedSplit[col][row];
				final Mat errorMat = errorSplit[col][row];

				errors[col][row] = new Error(config, originalMat, modifiedMat, errorMat);
			}
		}

		return errors;
	}

	private Pair<String[][], Double[][]> predictErrorSplit(IError[][] errorSplit) throws Exception
	{
		final int cols = errorSplit.length;
		final int rows = errorSplit[0].length;

		final String[][] classes = new String[cols][rows];
		final Double[][] predictions = new Double[cols][rows];
		final Pair<String[][], Double[][]> result = new Pair<>(classes, predictions);

		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				final IError error = errorSplit[col][row];
				final Pair<String, Double> predict = this.network.predict(error);

				classes[col][row] = predict.getKey();
				predictions[col][row] = predict.getValue();
			}
		}

		return result;
	}

	private Mat[][] splitImage(Mat image, int frameSize)
	{
		final Mat[][] result = Images.split(image, frameSize, frameSize);
		this.log.info("Split image.");

		return result;
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
