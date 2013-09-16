package com.thatsich.sample.opencv.metric;

import org.opencv.core.Mat;

import com.thatsich.core.opencv.metric.AMetric;

public class MaximumDistance extends AMetric {

	/**
	 * ||a - b||_\infty = max(abs(a_i - b_i))
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		// TODO Auto-generated method stub
		return 0;
	}

}
