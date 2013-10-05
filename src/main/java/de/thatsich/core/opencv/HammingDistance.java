package de.thatsich.core.opencv;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Calculates the Euclidean Distance between 2 Mats
 * @author Minh
 *
 */
public class HammingDistance extends AMetric {

	/**
	 * ||a - b||_2 = #(a_i != b_i)
	 */
	@Override
	public double getDistance(Mat original, Mat compare) throws IllegalStateException {
		if (original == null) throw new IllegalArgumentException("Original is null.");
		if (compare == null) throw new IllegalArgumentException("Compare is null.");
		if (original.type() != CvType.CV_8U) throw new IllegalArgumentException("Original is not grayscale.");
		if (compare.type() != CvType.CV_8U) throw new IllegalArgumentException("Compare is not grayscale.");
		if (compare.empty()) throw new IllegalArgumentException("Original is empty.");
		if (original.empty()) throw new IllegalArgumentException("Compare is empty.");
		if (!(original.size().equals(compare.size()))) throw new IllegalArgumentException("Size of Original and Compare differ.");
		
		// Core.NORM_HAMMING correspond to HammingDistance #(o != c)
		return Core.norm(original, compare, Core.NORM_HAMMING);
	}
}
