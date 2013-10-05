package de.thatsich.core.opencv;

import org.opencv.core.Mat;

public interface IErrorGenerator {
	public Mat generateError(Mat in);
	public String getName();
}
