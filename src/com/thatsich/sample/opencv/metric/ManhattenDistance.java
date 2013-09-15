package com.thatsich.sample.opencv.metric;

import org.opencv.core.Mat;

import com.thatsich.core.opencv.metric.IDistance;

public class ManhattenDistance implements IDistance {

	/**
	 * ||a - b||_1 = sum(abs(a_i - b_i))
	 */
	@Override
	public double getDistance(Mat original, Mat compare) {
		// TODO Auto-generated method stub
		return 0;
	}

}
