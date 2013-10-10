package de.thatsich.bachelor.javafx.business.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.bachelor.opencv.classifier.RandomForest;
import de.thatsich.bachelor.opencv.classifier.SVM;
import de.thatsich.bachelor.opencv.extractor.Gradient;
import de.thatsich.bachelor.opencv.extractor.GrayLevelCooccurenceHistogram;
import de.thatsich.bachelor.opencv.extractor.HuMoments;
import de.thatsich.bachelor.opencv.extractor.LocalBinaryPatternHistogram;
import de.thatsich.bachelor.opencv.extractor.Mean;
import de.thatsich.bachelor.opencv.extractor.Variance;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.IBinaryClassifier;
import de.thatsich.core.opencv.IFeatureExtractor;
import de.thatsich.core.opencv.Images;

public class EvaluationDatabase {
	
	// Properties
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	// Feature Extractor
	private final ObjectProperty<ObservableList<IFeatureExtractor>> featureExtractors = new ChoiceBox<IFeatureExtractor>().itemsProperty();
	private final ObjectProperty<IFeatureExtractor> selectedFeatureExtractor = new SimpleObjectProperty<IFeatureExtractor>();
	
	// Binary Classifier
	private final ObjectProperty<ObservableList<IBinaryClassifier>> binaryClassifiers = new ChoiceBox<IBinaryClassifier>().itemsProperty();
	private final ObjectProperty<IBinaryClassifier> selectedBinaryClassifier = new SimpleObjectProperty<IBinaryClassifier>();
	
	// ErrorEntry
	private final ObjectProperty<ObservableList<ErrorEntry>> errorEntries = new ChoiceBox<ErrorEntry>().itemsProperty();
	private final ObjectProperty<ErrorEntry> selectedErrorEntry = new SimpleObjectProperty<ErrorEntry>();
	
	
	// Injections
	private final Log log;
	
	/**
	 * Injected CTOR
	 * Initialize all Lists with default values if needed
	 * and set the first value to the first elem of the list
	 * if list is not empty.
	 * 
	 * @param log Injected Logger
	 */
	@Inject
	private EvaluationDatabase(Log log) {
		this.log = log;
		
		this.initFeatureExtractors();
		this.initBinaryClassifiers();
	}
	
	/**
	 * Initialize Feature Extracotrs
	 * - adding all known to the list
	 * - select the first in list if available
	 */
	private void initFeatureExtractors() {
		this.featureExtractors.get().addAll(
			new Gradient(),
			new GrayLevelCooccurenceHistogram(),
			new HuMoments(),
			new LocalBinaryPatternHistogram(),
			new Mean(),
			new Variance()
		);
		this.log.info("Initialized Feature Extractors.");
		
		if (this.featureExtractors.get().size() > 0) {
			this.selectedFeatureExtractor.set(this.featureExtractors.get().get(0));
			this.log.info("Initialized with first Feature Extractor.");
		}
	}
	
	/**
	 * Initialize Binary Classifiers
	 * - adding all known to the list
	 * - select the first in list if available
	 */
	private void initBinaryClassifiers() {
		this.binaryClassifiers.get().addAll(
			new RandomForest(this.log),
			new SVM(this.log)
		);
		this.log.info("Initialized Binary Classifiers.");
		
		if (this.binaryClassifiers.get().size() > 0) {
			this.selectedBinaryClassifier.set(this.binaryClassifiers.get().get(0));
			this.log.info("Initialized with first Binary Classifier.");
		}
	}
	
	/**
	 * Extracts features of the whole generated test-set
	 * except the one selected. And trains the selected
	 * Binary Classifier with the FeatureVectors
	 */
	public void trainBinaryClassifier() {
		IFeatureExtractor extractor = this.selectedFeatureExtractor.get();
		ErrorEntry testEntry = this.selectedErrorEntry.get();
		int frameSize = this.frameSize.get();
		
		if (extractor == null) throw new IllegalStateException("Extractor is null.");
		if (testEntry == null) throw new IllegalStateException("ErrorException is null.");
		if (frameSize == 0) throw new IllegalStateException("FrameSize is 0.");
		
		MatOfFloat positiveFeatureMat = new MatOfFloat();
		MatOfFloat negativeFeatureMat = new MatOfFloat();
//		Mat labelMatrix = new Mat();
		
		// Extracts all ErrorEntries but the selected one
		for (ErrorEntry entry : this.errorEntries.get()) {
			
//			System.out.println(Core.sumElems(entry.getOriginalWithErrorMat()));
			if (!entry.equals(testEntry)) {
				
				// split the image into frames to reduce dimension
				Mat[][] originalErrorSplit = Images.split(entry.getOriginalWithErrorMat(), frameSize, frameSize);
				Mat[][] errorSplit = Images.split(entry.getErrorMat(), frameSize, frameSize);
				
				for (int col = 0; col < originalErrorSplit.length; col++) {
					for (int row = 0; row < originalErrorSplit[col].length; row++) {
						Mat featureVector = extractor.extractFeature(originalErrorSplit[col][row]);

						// if contain an error classify it as positive match
						if (Core.sumElems(errorSplit[col][row]).val[0] != 0) {
							positiveFeatureMat.push_back(featureVector.t());
						}
						
						// else its a negative match
						else {
							negativeFeatureMat.push_back(featureVector.t());
						}
					}
				}
			}
		}
		
		this.selectedBinaryClassifier.get().train(positiveFeatureMat, negativeFeatureMat);
	}
	
	/**
	 * Tests the trained BinaryClassifier with the selected 
	 * Sample and saving the result into output folder
	 */
	// TODO implement testBC
	// TODO evtl die FrameSize und FeatureGenerator fest an den BinaryClassifier binden
	public void testBinaryClassifier() {
		int frameSize = this.frameSize.get();
		IFeatureExtractor extractor = this.selectedFeatureExtractor.get();
		ErrorEntry testEntry = this.selectedErrorEntry.get();
		
		// test each against it
		Mat[][] errorSplit = Images.split(testEntry.getErrorMat(), frameSize, frameSize);
		Mat result = new Mat(errorSplit.length, errorSplit[0].length, CvType.CV_32FC1);
		double max = 0;
		
		for (int col = 0; col < errorSplit.length; col++) {
			for (int row = 0; row < errorSplit[col].length; row++) {
				Mat featureVector = extractor.extractFeature(errorSplit[col][row]).t();
				featureVector.convertTo(featureVector, CvType.CV_32FC1);
//				this.log.info("FV with Size (" + featureVector.cols() + ", " + featureVector.rows() + ") is " + featureVector.type());
				
//				System.out.println(this.selectedBinaryClassifier.get().predict(featureVector));
				double prediction = this.selectedBinaryClassifier.get().predict(featureVector);
				
				result.put(row, col, prediction);
				
				max = Math.max(max, prediction);
				
				
			}
		}
		
//		System.out.println(result.dump());
		System.out.println(max);
		
//		Mat featureVector = extractor.extractFeature(errorSplit[0][0]).t();
//		featureVector.convertTo(featureVector, CvType.CV_32FC1);
//		this.log.info("FV with Size (" + featureVector.cols() + ", " + featureVector.rows() + ") is " + featureVector.type());
//		
//		System.out.println(this.selectedBinaryClassifier.get().predict(featureVector));
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================	
	public int getFrameSize() { return this.frameSize.get(); }
	
	// ==================================================
	// Setter Implementation
	// ==================================================
	public void setFrameSize(int frameSize) { this.frameSize.set(frameSize); } 
	
	// ==================================================
	// Property Implementation
	// ==================================================
	// Feature Extractors
	public ObjectProperty<ObservableList<IFeatureExtractor>> getFeatureExtractorsProperty() { return this.featureExtractors; }
	public ObjectProperty<IFeatureExtractor> getSelectedFeatureExtractorProperty() { return this.selectedFeatureExtractor; }
	
	// Binary Classifiers
	public ObjectProperty<ObservableList<IBinaryClassifier>> getBinaryClassifiersProperty() { return this.binaryClassifiers; }
	public ObjectProperty<IBinaryClassifier> getSelectedBinaryClassifierProperty() { return this.selectedBinaryClassifier; }
	
	// Error Entries
	public ObjectProperty<ObservableList<ErrorEntry>> getErrorEntriesProperty() { return this.errorEntries; }
	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty() { return this.selectedErrorEntry; }
}

