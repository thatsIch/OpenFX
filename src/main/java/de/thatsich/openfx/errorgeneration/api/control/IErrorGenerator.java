package de.thatsich.openfx.errorgeneration.api.control;

import org.opencv.core.Mat;

public interface IErrorGenerator
{
	Mat generateError(Mat in);

	String getName();
}
