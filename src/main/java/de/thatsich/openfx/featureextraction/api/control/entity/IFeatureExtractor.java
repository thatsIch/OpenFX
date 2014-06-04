package de.thatsich.openfx.featureextraction.api.control.entity;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

public interface IFeatureExtractor
{
	MatOfFloat extractFeature(Mat image);

	String getName();
}
