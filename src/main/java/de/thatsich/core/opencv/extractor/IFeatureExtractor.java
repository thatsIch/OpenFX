package de.thatsich.core.opencv.extractor;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

public interface IFeatureExtractor {
	public MatOfFloat extractFeature(Mat image);
	public String getName();
}
