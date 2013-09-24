package de.thatsich.core.opencv.metric;

import org.opencv.core.Mat;

public interface IMetric {
	public double getDistance(Mat original, Mat compare);
	public String getName();
}
