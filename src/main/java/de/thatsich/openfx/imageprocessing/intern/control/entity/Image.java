package de.thatsich.openfx.imageprocessing.intern.control.entity;

import de.thatsich.openfx.imageprocessing.api.control.IImage;
import org.opencv.core.Mat;

public class Image implements IImage
{
	private final Mat imageMat;
	private final ImageConfig config;

	public Image(ImageConfig config, Mat imageMat)
	{
		this.config = config;
		this.imageMat = imageMat;
	}

	@Override
	public Mat getImageMat()
	{
		return this.imageMat;
	}

	@Override
	public String getImageName()
	{
		return this.config.imageName;
	}

	@Override
	public ImageConfig getConfig()
	{
		return this.config;
	}
}
