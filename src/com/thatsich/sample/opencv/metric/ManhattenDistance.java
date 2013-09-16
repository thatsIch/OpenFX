package com.thatsich.sample.opencv.metric;

import org.opencv.core.Mat;

import com.thatsich.core.opencv.metric.AMetric;

public class ManhattenDistance extends AMetric {

	/**
	 * ||a - b||_1 = sum(abs(a_i - b_i))
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		// TODO Auto-generated method stub
		return 0;
	}
}
