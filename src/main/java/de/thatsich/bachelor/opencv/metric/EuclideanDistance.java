package de.thatsich.bachelor.opencv.metric;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import de.thatsich.core.opencv.metric.AMetric;

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
		if (original == null) throw new IllegalArgumentException("Original is null.");
		if (compare == null) throw new IllegalArgumentException("Compare is null.");
		if (original.type() != CvType.CV_8U) throw new IllegalArgumentException("Original is not grayscale.");
		if (compare.type() != CvType.CV_8U) throw new IllegalArgumentException("Compare is not grayscale.");
		if (compare.empty()) throw new IllegalArgumentException("Original is empty.");
		if (original.empty()) throw new IllegalArgumentException("Compare is empty.");
		if (!(original.size().equals(compare.size()))) throw new IllegalArgumentException("Size of Original and Compare differ.");
		
		// Core.NORM_L2 correspond to EuclideanDistance sqrt(sum(o - c)^2)
		return Core.norm(original, compare, Core.NORM_L2);
	}
}