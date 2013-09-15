package com.thatsich.sample.opencv.metric;

import org.opencv.core.Mat;

import com.thatsich.core.opencv.metric.IDistance;

public class SquaredEuclideanDistance implements IDistance {

	/**
	 * ||a - b||^2_2 = sum(a_i - b_i)^2
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		// TODO Auto-generated method stub
		return 0;
	}

}
