package de.thatsich.bachelor.javafx.business.model.entity;

import org.opencv.core.MatOfFloat;

public class FeatureVector {
	
	private final MatOfFloat featureVector;
	private final MatOfFloat classificationLabel;
	
	public FeatureVector(MatOfFloat featureVector, MatOfFloat classificationLabel) {
		this.featureVector = featureVector;
		this.classificationLabel = classificationLabel;
	}
	
	// TODO
	public FeatureVector(String featureVectorLabelString) {
		String[] featureVector = featureVectorLabelString.split(",");
		
		
		this.featureVector = null;
		this.classificationLabel = null;
	}

	public MatOfFloat getFeatureVector() {
		return this.featureVector;
	}

	public MatOfFloat getClassificationLabel() {
		return this.classificationLabel;
	}
}
