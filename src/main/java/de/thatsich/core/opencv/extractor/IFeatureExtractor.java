package de.thatsich.core.opencv.extractor;

import org.opencv.core.Mat;

public interface IFeatureExtractor {
	public Mat extractFeature(Mat image);
	public String getName();
}
