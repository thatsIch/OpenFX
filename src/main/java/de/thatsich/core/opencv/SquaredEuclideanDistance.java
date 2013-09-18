package de.thatsich.core.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class SquaredEuclideanDistance extends AMetric {

	/**
	 * ||a - b||^2_2 = sum(a_i - b_i)^2
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		
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
		
		return result;
	}

}
