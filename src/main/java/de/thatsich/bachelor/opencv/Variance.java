package de.thatsich.bachelor.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;

import de.thatsich.core.opencv.AFeatureExtractor;
import de.thatsich.core.opencv.IFeatureExtractor;

public class Variance extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {
		MatOfDouble variance = new MatOfDouble();
		Core.meanStdDev(image, new MatOfDouble(), variance);

		return variance;
	}

	@Override
	public double compareFeature(Mat leftImage, Mat rightImage) {
		// TODO Auto-generated method stub
		return 0;
	}

}
