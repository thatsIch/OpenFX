package de.thatsich.core.opencv.metric;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Calculates the Euclidean Distance between 2 Mats
 * @author Minh
 *
 */
public class EuclideanDistance extends AMetric {

	/**
	 * ||a - b||_2 = SQRT(sum(a_i - b_i)^2)
	 */
	@Override
	public double getDistance(Mat original, Mat compare) throws IllegalStateException {

		if (!(original.size().equals(compare.size()))) throw new IllegalStateException("Size of original and compare differ.");
		this.log.info("Tested for same size.");
		
		if (original.channels() != 1 && compare.channels() != 1) throw new IllegalStateException("Not GrayScale.");
		this.log.info("Tested for GrayScale images");
		
		Mat diff = new Mat(original.size(), CvType.CV_8UC1);
		Core.absdiff(original, compare, diff);
		this.log.info("Creating diff mat.");
		
		Core.pow(diff, 2, diff);
		this.log.info("Sqaure each element within.");
	
		double result = Core.sumElems(diff).val[0];
		this.log.info("Sum all elements together.");
		
		result = Math.sqrt(result);
		this.log.info("Squareroot the sum.");
				
		return 0;
	}
}
