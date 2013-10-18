//package de.thatsich.bachelor.javafx.business.model;
//
//import javafx.beans.property.IntegerProperty;
//import javafx.beans.property.ListProperty;
//import javafx.beans.property.ObjectProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleListProperty;
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.collections.FXCollections;
//
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfFloat;
//
//import com.google.inject.Inject;
//
//import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
//import de.thatsich.bachelor.opencv.classifier.RandomForest;
//import de.thatsich.bachelor.opencv.classifier.SVM;
//import de.thatsich.core.Log;
//import de.thatsich.core.opencv.IBinaryClassifier;
//import de.thatsich.core.opencv.IFeatureExtractor;
//import de.thatsich.core.opencv.Images;
//
//public class EvaluationDatabase {
//	
//	/**
//	 * Extracts features of the whole generated test-set
//	 * except the one selected. And trains the selected
//	 * Binary Classifier with the FeatureVectors
//	 */
//	public void trainBinaryClassifier() {
//		IFeatureExtractor extractor = this.selectedFeatureExtractor.get();
//		ErrorEntry testEntry = this.selectedErrorEntry.get();
//		int frameSize = this.frameSize.get();
//		
//		if (extractor == null) throw new IllegalStateException("Extractor is null.");
//		if (testEntry == null) throw new IllegalStateException("ErrorException is null.");
//		if (frameSize == 0) throw new IllegalStateException("FrameSize is 0.");
//		
//		MatOfFloat positiveFeatureMat = new MatOfFloat();
//		MatOfFloat negativeFeatureMat = new MatOfFloat();
////		Mat labelMatrix = new Mat();
//		
//		// Extracts all ErrorEntries but the selected one
//		for (ErrorEntry entry : this.errorEntries.get()) {
//			
////			System.out.println(Core.sumElems(entry.getOriginalWithErrorMat()));
//			if (!entry.equals(testEntry)) {
//				
//				// split the image into frames to reduce dimension
//				Mat[][] originalErrorSplit = Images.split(entry.getOriginalWithErrorMat(), frameSize, frameSize);
//				Mat[][] errorSplit = Images.split(entry.getErrorMat(), frameSize, frameSize);
//				
//				for (int col = 0; col < originalErrorSplit.length; col++) {
//					for (int row = 0; row < originalErrorSplit[col].length; row++) {
//						Mat featureVector = extractor.extractFeature(originalErrorSplit[col][row]);
//
//						// if contain an error classify it as positive match
//						if (Core.sumElems(errorSplit[col][row]).val[0] != 0) {
//							positiveFeatureMat.push_back(featureVector.t());
//						}
//						
//						// else its a negative match
//						else {
//							negativeFeatureMat.push_back(featureVector.t());
//						}
//					}
//				}
//			}
//		}
//		
//		this.selectedBinaryClassifier.get().train(positiveFeatureMat, negativeFeatureMat);
//	}
//	
//	/**
//	 * Tests the trained BinaryClassifier with the selected 
//	 * Sample and saving the result into output folder
//	 */
//	// TODO implement testBC
//	// TODO evtl die FrameSize und FeatureGenerator fest an den BinaryClassifier binden
//	public void testBinaryClassifier() {
//		int frameSize = this.frameSize.get();
//		IFeatureExtractor extractor = this.selectedFeatureExtractor.get();
//		ErrorEntry testEntry = this.selectedErrorEntry.get();
//		
//		// test each against it
//		Mat[][] errorSplit = Images.split(testEntry.getErrorMat(), frameSize, frameSize);
//		Mat result = new Mat(errorSplit.length, errorSplit[0].length, CvType.CV_32FC1);
//		double max = 0;
//		
//		for (int col = 0; col < errorSplit.length; col++) {
//			for (int row = 0; row < errorSplit[col].length; row++) {
//				Mat featureVector = extractor.extractFeature(errorSplit[col][row]).t();
//				featureVector.convertTo(featureVector, CvType.CV_32FC1);
////				this.log.info("FV with Size (" + featureVector.cols() + ", " + featureVector.rows() + ") is " + featureVector.type());
//				
////				System.out.println(this.selectedBinaryClassifier.get().predict(featureVector));
//				double prediction = this.selectedBinaryClassifier.get().predict(featureVector);
//				
//				result.put(row, col, prediction);
//				
//				max = Math.max(max, prediction);
//				
//				
//			}
//		}
//	}
//}
//
