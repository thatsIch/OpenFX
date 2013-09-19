package de.thatsich.bachelor.opencv;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.core.Log;
import de.thatsich.core.opencv.AFeatureExtractor;
import de.thatsich.core.opencv.IFeatureExtractor;

/**
 * 
 * @author Minh
 *
 */
@Singleton
public class HuMoments extends AFeatureExtractor implements IFeatureExtractor {

	/**
	 * 
	 */
	@Inject private Log log;
	
	/**
	 * 
	 */
	public HuMoments() {
		
	}
	
	/**
	 * 
	 */
	@Override
	public Mat extractFeature(Mat image) {		
		
		if (image == null || image.empty()) throw new IllegalStateException("HuMoments.etractFeature: Empty Image");
		
		Mat result = new Mat();
		Moments moments = Imgproc.moments(image);
		Imgproc.HuMoments(moments, result);
		this.log.info("Feature extracted: " + result.dump());
		
		return result;
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
