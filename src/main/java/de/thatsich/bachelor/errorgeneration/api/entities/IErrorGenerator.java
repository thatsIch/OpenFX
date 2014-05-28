package de.thatsich.bachelor.errorgeneration.api.entities;

import org.opencv.core.Mat;

public interface IErrorGenerator
{
	public Mat generateError(Mat in);

	public String getName();
}
