package de.thatsich.bachelor.errorgeneration.api.entities;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.image.Image;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import de.thatsich.core.opencv.Images;

public class ErrorEntry {
	private final Mat originalMat;
	private final Mat errorMat;
	private final Mat originalWithError;
	private final ReadOnlyStringWrapper errorClass = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper errorName = new ReadOnlyStringWrapper();
	private final ReadOnlyObjectWrapper<Mat> mergedMat = new ReadOnlyObjectWrapper<Mat>();
	
	private final Path storagePath;
	
	/**
	 * Generating ErrorEntry CTOR
	 * 
	 * @param originalMat
	 * @param errorMat
	 * @param storagePath
	 */
	public ErrorEntry(Mat originalMat, Mat originalWithErrorMat, Path storagePath) {
		this.storagePath = storagePath;
		this.initClassAndName(storagePath);
		
		this.originalMat = originalMat;
		this.originalWithError = originalWithErrorMat;
		this.errorMat = new Mat();
		Core.absdiff(originalMat, originalWithErrorMat, this.errorMat);
		
		// Save the original, original with error and the error into each color layer
		List<Mat> listMat = Arrays.asList(this.originalMat, this.originalWithError, this.errorMat);
		Mat mergedMat = new Mat(originalMat.size(), CvType.CV_8UC3);
		Core.merge(listMat, mergedMat);
		this.mergedMat.set(mergedMat);	
	}
	
	/**
	 * Reading ErrorEntry CTOR
	 * 
	 * @param storagePath
	 */
	public ErrorEntry(Path storagePath) {
		this.storagePath = storagePath;
		this.initClassAndName(storagePath);
		
		Mat encodedImage = Images.toMat(storagePath);
		
		// split channels to extract GL and Error Mat
		List<Mat> encodedImageChannelMats = new ArrayList<Mat>(); 
		Core.split(encodedImage, encodedImageChannelMats);
		
		this.originalMat = encodedImageChannelMats.get(0);
		this.originalWithError = encodedImageChannelMats.get(1);
		this.errorMat = encodedImageChannelMats.get(2);
		this.mergedMat.set(encodedImage);
	}
	
	/** 
	 * Handle common code between the constructors
	 */
	private void initClassAndName(Path storagePath) {
		// Defining Pattern and Matcher
		final Pattern errorClassPattern = Pattern.compile("[a-zA-Z]+");
		final String classAndName = storagePath.getFileName().toString();
		final Matcher m = errorClassPattern.matcher(classAndName);
		
		// Searching first instance
		m.find();
		String clazz = m.group();
		
		// Store
		this.errorClass.set(clazz);
		this.errorName.set(classAndName.replace(clazz, ""));
	}
	
	// ==================================================
	// Getter Implementation
	// ==================================================
	// Properties
	public ReadOnlyStringProperty getErrorClassProperty() { return this.errorClass.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getErrorNameProperty() { return this.errorName.getReadOnlyProperty(); }
	
	// Values
	public Mat getOriginalMat() { return this.originalMat; }
	public Mat getErrorMat() { return this.errorMat; }
	public Mat getOriginalWithErrorMat() { return this.originalWithError; }
	public Mat getMergedMat() { return this.mergedMat.get(); }
	public Image getImage() { return Images.toImage(this.getOriginalWithErrorMat()); }
	public Path getPath() { return this.storagePath; }
	
}
