package de.thatsich.bachelor.opencv.metric;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.core.Log;
import de.thatsich.core.opencv.metric.AMetric;

/**
 * Metric which uses the maximum distance to distinguish two mats
 * 
 * @author Minh
 *
 */
public class MaximumDistance extends AMetric {

	@Inject private Log log;
	
	/**
	 * ||a - b||_\infty = max(abs(a_i - b_i))
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
		
		MinMaxLocResult minMax = Core.minMaxLoc(diff);
		this.log.info("Maximum found: " + minMax.maxVal);
		
		return minMax.maxVal;
	}

}
