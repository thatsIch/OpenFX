package de.thatsich.bachelor.classificationtraining.api.entities;

import org.opencv.core.Mat;

public interface IBinaryClassification {
	public String getName();
	public double predict(Mat image);
	public void load(String fileName);
	public void save(String fileName);
}
