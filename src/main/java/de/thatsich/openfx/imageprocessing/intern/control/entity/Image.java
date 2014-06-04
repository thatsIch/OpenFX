package de.thatsich.openfx.imageprocessing.intern.control.entity;

import de.thatsich.openfx.imageprocessing.api.control.IImage;
import org.opencv.core.Mat;

public class Image implements IImage
{
	private final Mat imageMat;
	private final String imageName;

	public Image(Mat imageMat, String imageName)
	{
		this.imageMat = imageMat;
		this.imageName = imageName;
	}

	@Override
	public Mat getImageMat()
	{
		return this.imageMat;
	}

	@Override
	public String getImageName()
	{
		return this.imageName;
	}
}
