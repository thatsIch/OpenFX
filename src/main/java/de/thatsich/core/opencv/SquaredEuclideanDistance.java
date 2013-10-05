package de.thatsich.core.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Squared Euclidiean distance to be more aggressive vs higher values
 * 
 * @author Minh
 *
 */
public class SquaredEuclideanDistance extends AMetric {
	/**
	 * ||a - b||^2_2 = sum(a_i - b_i)^2
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		if (original == null) throw new IllegalArgumentException("Original is null.");
		if (compare == null) throw new IllegalArgumentException("Compare is null.");
		if (original.type() != CvType.CV_8U) throw new IllegalArgumentException("Original is not grayscale.");
		if (compare.type() != CvType.CV_8U) throw new IllegalArgumentException("Compare is not grayscale.");
		if (compare.empty()) throw new IllegalArgumentException("Original is empty.");
		if (original.empty()) throw new IllegalArgumentException("Compare is empty.");
		if (!(original.size().equals(compare.size()))) throw new IllegalArgumentException("Size of Original and Compare differ.");
		
		// Core.NORM_L2 correspond to EuclideanDistance sum(o - c)^2
		return Core.norm(original, compare, Core.NORM_L2SQR);
	}
}
