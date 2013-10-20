package de.thatsich.bachelor.imageprocessing.api.entities;

import java.nio.file.Path;

import javafx.scene.image.Image;

import org.opencv.core.Mat;

import de.thatsich.core.opencv.Images;

public class ImageEntry {
	private Mat imageMat;
	private Path imagePath;
	
	public ImageEntry(Path imagePath) {
		this.imageMat = Images.toMat(imagePath);
		this.imageMat = Images.toGrayScale(this.imageMat);
		this.imagePath = imagePath;
	}
	
	public ImageEntry(Mat imageMat, Path imagePath) {
		this.imageMat = Images.toGrayScale(imageMat);
		this.imagePath = imagePath;
	}
	
	public void store() {
		Images.store(this.imageMat, this.imagePath);
	}
	
	public Image getImage() {
		return Images.toImage(this.imageMat);
	}
	
	public Mat getMat() {
		return this.imageMat;
	}
	
	public Path getPath() {
		return this.imagePath;
	}
	
	public String getName() {
		return this.imagePath.getFileName().toString();
	}
}
