package de.thatsich.core.opencv;

import org.opencv.core.Mat;

public interface IFeatureExtractor {
	public Mat extractFeature(Mat image);
	public double compareFeature(Mat leftImage, Mat rightImage);
	public String getName();
}
