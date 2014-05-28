package de.thatsich.bachelor.imageprocessing.api.entities;

import de.thatsich.core.javafx.IEntity;
import org.opencv.core.Mat;

import java.nio.file.Path;

public class ImageEntry implements IEntity
{
	private final Mat imageMat;
	private final Path imagePath;

	public ImageEntry(Path imagePath, Mat imageMat)
	{
		this.imageMat = imageMat;
		this.imagePath = imagePath;
	}

	@Override
	public String getName()
	{
		return this.imagePath.getFileName().toString();
	}

	public Mat getImageMat()
	{
		return imageMat;
	}

	public Path getImagePath() { return this.imagePath; }
}
