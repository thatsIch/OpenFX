package de.thatsich.core.opencv.error;

import org.opencv.core.Mat;

public interface IErrorGenerator {
	public Mat generateError(Mat in);
	public String getName();
}
