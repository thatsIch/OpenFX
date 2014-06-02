package de.thatsich.bachelor.errorgeneration.api.control;

import org.opencv.core.Mat;

public interface IErrorGenerator
{
	Mat generateError(Mat in);

	String getName();
}
