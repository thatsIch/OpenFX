package de.thatsich.bachelor.opencv.extractor;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;

import de.thatsich.core.opencv.extractor.AFeatureExtractor;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

public class Variance extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
				
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
