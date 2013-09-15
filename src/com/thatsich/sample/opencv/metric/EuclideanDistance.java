package com.thatsich.sample.opencv.metric;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.thatsich.core.opencv.metric.ADistance;

public class EuclideanDistance extends ADistance {

	/**
	 * ||a - b||_2 = SQRT(sum(a_i - b_i)^2)
	 */
	@Override
	public double getDistance(Mat original, Mat compare) throws IllegalStateException {
		
		double result = 0;
		
		// Test same size
		if (!(original.size().equals(compare.size()))) throw new IllegalStateException("Size of original and compare differ.");
		
		// check if really grayscale
		if (original.channels() != 1 && compare.channels() != 1) throw new IllegalStateException("Not GrayScale.");
	
		// new grayscale image of size to display the difference
		Mat diff = new Mat(original.size(), CvType.CV_8UC1);
		Core.absdiff(original, compare, diff);
		
		// square each
		Core.pow(diff, 2, diff);
		
		// sum all
		result = Core.sumElems(diff).val[0];
	
		// root it
		result = Math.sqrt(result);
		
		return 0;
	}

}
