package de.thatsich.bachelor.imageprocessing.api.entities;

import java.nio.file.Path;

import org.opencv.core.Mat;

public class ImageEntry {
	private final Mat imageMat;
	private final Path imagePath;
	
	public ImageEntry(Path imagePath, Mat imageMat) {
		this.imageMat = imageMat;
		this.imagePath = imagePath;
	}
	
	public Mat getImageMat() {
		return this.imageMat;
	}
	
	public Path getImagePath() {
		return this.imagePath;
	}
	
	public String getName() {
		return this.imagePath.getFileName().toString();
	}
}
