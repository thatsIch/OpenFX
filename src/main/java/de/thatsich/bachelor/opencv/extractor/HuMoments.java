package de.thatsich.bachelor.opencv.extractor;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.google.inject.Singleton;

import de.thatsich.core.opencv.extractor.AFeatureExtractor;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

/**
 * 
 * @author Minh
 *
 */
@Singleton
public class HuMoments extends AFeatureExtractor implements IFeatureExtractor {

	@Override
	public Mat extractFeature(Mat image) {		
		if (image == null) throw new IllegalArgumentException("Image is null.");
		if (image.type() != CvType.CV_8U) throw new IllegalArgumentException("Image is not grayscale.");
		if (image.empty()) throw new IllegalArgumentException("Image is empty.");
		
		Mat hu = new Mat();
		Moments moments = Imgproc.moments(image);
		Imgproc.HuMoments(moments, hu);
		
		return hu;
	}

	/**
	 * 
	 */
	@Override
	public double compareFeature(Mat leftImage, Mat rightImage) {

		final Mat leftResult = this.extractFeature(leftImage);
		@SuppressWarnings("unused")
		final Mat rightResult = this.extractFeature(rightImage);

		// both images needs to be same size I think
		if (!leftImage.size().equals(rightImage.size())) throw new IllegalStateException("HuMoments.compareFeature: Invalid Sizes " + leftImage.size() + ", " + rightImage.size()); 
		
		@SuppressWarnings("unused")
		double distance = 0;
		for (int row = 0; row < leftResult.rows(); row++) {
			for (int col = 0; col < leftResult.cols(); col++) {
				double value = leftResult.get(row, col)[0];
				System.out.print(value);
				// Transform
				value = (-1) * Math.signum(value) * Math.log10(Math.abs(value));
				System.out.println(", " + value);
				
			}
		}
		
		return 0;
	}
}
