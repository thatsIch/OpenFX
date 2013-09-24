package de.thatsich.bachelor.opencv.metric;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.core.Log;
import de.thatsich.core.opencv.metric.AMetric;

/**
 * Squared Euclidiean distance to be more aggressive vs higher values
 * 
 * @author Minh
 *
 */
public class SquaredEuclideanDistance extends AMetric {

	/**
	 * Injected Logger
	 */
	@Inject private Log log;
	
	/**
	 * ||a - b||^2_2 = sum(a_i - b_i)^2
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		
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
		this.log.info("Result is " + result);
		
		return result;
	}
}
