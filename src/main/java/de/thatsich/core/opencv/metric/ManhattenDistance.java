package de.thatsich.core.opencv.metric;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.core.Log;

/**
 * Calculates the Manhatten Distance
 * 
 * @author Minh
 *
 */
public class ManhattenDistance extends AMetric {

	/**
	 * Injected Logger
	 */
	@Inject private Log log;
	
	/**
	 * ||a - b||_1 = sum(abs(a_i - b_i))
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		double result = 0;
		
		if (!(original.size().equals(compare.size()))) throw new IllegalStateException("Size of original and compare differ.");
		this.log.info("Checked for size: " + original.size() + ", " + compare.size() + " size.");
		
		if (original.channels() != 1 && compare.channels() != 1) throw new IllegalStateException("Not GrayScale.");
		this.log.info("Checked for grayscale: " + original.channels() + ", " + compare.channels() + " channels.");
		
		Mat diff = new Mat(original.size(), CvType.CV_8UC1);
		Core.absdiff(original, compare, diff);
		System.out.println(compare.dump());
		this.log.info("Absolute difference between both Mats.");
		
		result = Core.sumElems(diff).val[0];
		this.log.info("Sum: " + result);
		
		return 0;
	}
}
