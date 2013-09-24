package de.thatsich.bachelor.opencv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import de.thatsich.core.opencv.AFeatureExtractor;
import de.thatsich.core.opencv.IFeatureExtractor;

public class Gradient extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {
		
		Mat result = new Mat(image.rows(), image.cols(), image.type());
		
		Imgproc.Sobel(image, result, image.depth(), 1, 1);
		
		return result;
	}

	@Override
	public double compareFeature(Mat leftImage, Mat rightImage) {
		// TODO Auto-generated method stub
		return 0;
	}

}
