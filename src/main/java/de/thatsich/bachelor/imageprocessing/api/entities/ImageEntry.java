package de.thatsich.bachelor.imageprocessing.api.entities;

import java.nio.file.Path;

import lombok.Getter;

import org.opencv.core.Mat;

import de.thatsich.core.javafx.IEntity;

public class ImageEntry implements IEntity {
	
	@Getter
	private final Mat imageMat;
	
	@Getter
	private final Path imagePath;
	
	public ImageEntry( Path imagePath, Mat imageMat )
	{
		this.imageMat = imageMat;
		this.imagePath = imagePath;
	}
	
	@Override
	public String getName() {
		return this.imagePath.getFileName().toString();
	}
}
