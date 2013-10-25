package de.thatsich.bachelor.classificationtesting.restricted.controller.commands;

import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.UUID;

import javafx.concurrent.Task;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.services.BinaryPredictionFileStorageService;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.Images;

public class TestBinaryClassificationCommand extends Command<BinaryPrediction> {

	// Properties
	private final Path predictionFolderPath;
	private final ImageEntry imageEntry;
	private final int frameSize;
	private final IErrorGenerator errorGenerator;
	private final IFeatureExtractor featureExtractor;
	private final IBinaryClassification binaryClassification;
	
	// Injections
	@Inject private BinaryPredictionFileStorageService fileStorage;
	
	@Inject
	private TestBinaryClassificationCommand(
			@Assisted Path predictionFolderPath,
			@Assisted ImageEntry imageEntry,
			@Assisted int frameSize,
			@Assisted IErrorGenerator errorGenerator,
			@Assisted IFeatureExtractor featureExtractor,
			@Assisted IBinaryClassification binaryClassification) {
		this.predictionFolderPath = predictionFolderPath;
		this.imageEntry = imageEntry;
		this.frameSize = frameSize;
		this.errorGenerator = errorGenerator;
		this.featureExtractor = featureExtractor;
		this.binaryClassification = binaryClassification;
	}
	
	@Override
	protected Task<BinaryPrediction> createTask() {
		return new Task<BinaryPrediction>() {

			@Override
			/*
			 * Extracts error and determines if a frame has an Error 
			 * which will be placed in the second layer.
			 * You run through the prediction of the frame withError
			 * and multiple the prediction value (between 0 and 1)
			 * with 255 to get a decent color value. This will be placed
			 * into the third image layer. 
			 */
			protected BinaryPrediction call() throws Exception {
				final Mat withoutError = imageEntry.getMat();
				final Mat withError = generateError(withoutError);
				final Mat onlyError = onlyError(withoutError, withError);
				final Mat[][] withErrorSplit = splitImage(withError, frameSize);
				final Mat[][] onlyErrorSplit = splitImage(onlyError, frameSize);
				final Mat[][] errorIndicationSplit = getErrorIndicationMat(onlyErrorSplit);
				final Mat errorIndicationMat = assembleImage(errorIndicationSplit, onlyError.size());
				final MatOfFloat[][] featureVectorSplit = getFeatureVectorSplit(withErrorSplit, featureExtractor);
				final Mat[][] predictionMatSplit = predictError(featureVectorSplit, frameSize, binaryClassification);
				final Mat predictionMat = assembleImage(predictionMatSplit, onlyError.size());
				log.info("Prepared Image Content.");
				
				final StringBuffer fileName = new StringBuffer();
				fileName.append(binaryClassification.getName() + "_");
				fileName.append(featureExtractor.getName() + "_");
				fileName.append(frameSize + "_");
				fileName.append(errorGenerator.getName() + "_");
				fileName.append(UUID.randomUUID().toString() + ".png");
				log.info("Prepared Image FileName.");
				
				final Path filePath = predictionFolderPath.resolve(fileName.toString());
				log.info("Resolved FileName.");
				
				final BinaryPrediction prediction = new BinaryPrediction(filePath, withError, errorIndicationMat, predictionMat);
				log.info("Created Binary Prediction.");
				
				fileStorage.save(prediction);
				log.info("Stored Prediction to FileSystem.");
				
				return prediction;
			}
			
			
		};
	}

	private Mat generateError(Mat image) {
		final Mat clone = image.clone();
		final Mat error = errorGenerator.generateError(clone);
		log.info("Generated Error.");
		
		return error;
	}
	
	private Mat onlyError(Mat withoutError, Mat withError) {
		Mat result = new Mat();
		Core.absdiff(withError, withoutError, result);
		log.info("Extracted only Error.");
		
		return result;
	}
	
	private MatOfFloat[][] getFeatureVectorSplit(Mat[][] withErrorSplit, IFeatureExtractor extractor) {
		final int cols = withErrorSplit.length;
		final int rows = withErrorSplit[0].length;
		
		final MatOfFloat[][] featureVectorSplit = new MatOfFloat[cols][rows];
		
		for (int col = 0; col < withErrorSplit.length; col++) {
			for (int row = 0; row < withErrorSplit[col].length; row++) {
				featureVectorSplit[col][row] = extractor.extractFeature(withErrorSplit[col][row]);
			}
		}
		
		return featureVectorSplit;
	}
		
	private Mat getErrorIndicationMat(Mat onlyErrorSplitPart) {
		if (Core.sumElems(onlyErrorSplitPart).val[0] != 0) {
			final Scalar value = new Scalar(255);
			return new Mat(onlyErrorSplitPart.size(), onlyErrorSplitPart.type(), value);
		} else {
			return Mat.zeros(onlyErrorSplitPart.size(), onlyErrorSplitPart.type());
		}
	}
	
	private Mat getPredictionMat(int matSize, double prediction) {
		if (prediction < 0 || 1 < prediction) throw new InvalidParameterException("Prediction out of bound [0,1]: " + prediction);
		
		final Scalar value = new Scalar(255 * prediction);
		final Mat predictionMat = new Mat(matSize, matSize, CvType.CV_8UC1, value);
		this.log.info("Created PreditionMat.");
		
		return predictionMat;
	}
	
	private Mat[][] getErrorIndicationMat(Mat[][] onlyErrorSplit) {
		final int cols = onlyErrorSplit.length;
		final int rows = onlyErrorSplit[0].length;
		
		final Mat[][] errorIndicationMat = new Mat[cols][rows];
		
		for (int col = 0; col < onlyErrorSplit.length; col++) {
			for (int row = 0; row < onlyErrorSplit[col].length; row++) {
				errorIndicationMat[col][row] = getErrorIndicationMat(onlyErrorSplit[col][row]);
			}
		}
		
		return errorIndicationMat;
	}
	
	private Mat[][] predictError(MatOfFloat[][] featureVectorSplit, int predictionFrameSize, IBinaryClassification classification) {
		final int cols = featureVectorSplit.length;
		final int rows = featureVectorSplit[0].length;
		
		final Mat[][] predictionMatSplit = new Mat[cols][rows];
		
		for (int col = 0; col < featureVectorSplit.length; col++) {
			for (int row = 0; row < featureVectorSplit[col].length; row++) {
				final double prediction = classification.predict(featureVectorSplit[col][row].t());
				predictionMatSplit[col][row] = getPredictionMat(predictionFrameSize, prediction);
			}
		}
		
		return predictionMatSplit;
	}
	
	private Mat[][] splitImage(Mat image, int frameSize) {
		final Mat[][] result = Images.split(image, frameSize, frameSize);
		this.log.info("Split image.");
		
		return result;
	}
	
	private Mat assembleImage(Mat[][] split, Size assembledSize) {
		final Mat assembly = Mat.zeros(assembledSize, CvType.CV_8UC1);
		
		for (int col = 0; col < split.length; col++) {
			for (int row = 0; row < split[col].length; row++) {
				final Mat splitPart = split[col][row];
				final int frameSize = splitPart.rows();
				final Mat roi = assembly.submat(frameSize * row, frameSize * (row + 1), frameSize * col, frameSize * (col + 1));
				splitPart.copyTo(roi);
			}
		}
		
		return assembly;
	}
}
