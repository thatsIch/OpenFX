package de.thatsich.core.opencv;

import org.opencv.core.MatOfFloat;

public class FeatureVectorList {

	private MatOfFloat featureVectors;
	private MatOfFloat classificationLabels;
	
	public FeatureVectorList() {
		
	}
	
	public void addFeatureVector(FeatureVector featureVector) {
		this.featureVectors.push_back(featureVector.getFeatureVector().t());
		this.classificationLabels.push_back(featureVector.getClassificationLabel().t());
	}
	
	public MatOfFloat getFeatureVectors() {
		return this.featureVectors;
	}
	
	public MatOfFloat getClassificationLabels() {
		return this.classificationLabels;
	}
}
