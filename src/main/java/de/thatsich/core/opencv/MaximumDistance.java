package de.thatsich.core.opencv;

import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MaximumDistance extends AMetric {

	/**
	 * ||a - b||_\infty = max(abs(a_i - b_i))
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
				
		// Test same size
		if (!(original.size().equals(compare.size()))) throw new IllegalStateException("Size of original and compare differ.");
		
		// check if really grayscale
		if (original.channels() != 1 && compare.channels() != 1) throw new IllegalStateException("Not GrayScale.");
	
		// new grayscale image of size to display the difference
		Mat diff = new Mat(original.size(), CvType.CV_8UC1);
		Core.absdiff(original, compare, diff);
		
		MinMaxLocResult minMax = Core.minMaxLoc(diff);
		
		return minMax.maxVal;
	}

}
