package de.thatsich.bachelor.errorgeneration.api.entities;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import lombok.Getter;

import org.opencv.core.Mat;


public class ErrorEntry
{
	@Getter
	private final Mat							originalMat;
	
	@Getter
	private final Mat							errorMat;
	
	@Getter
	private final Mat							originalWithError;
	private final ReadOnlyStringWrapper			errorClass		= new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper			errorName		= new ReadOnlyStringWrapper();

	@Getter
	private final Path							storagePath;

	// /**
	// * Generating ErrorEntry CTOR
	// *
	// * @param originalMat
	// * @param errorMat
	// * @param storagePath
	// */
	// public ErrorEntry(Mat originalMat, Mat originalWithErrorMat, Path
	// storagePath) {
	// this.storagePath = storagePath;
	// this.initClassAndName(storagePath);
	//
	// this.originalMat = originalMat;
	// this.originalWithError = originalWithErrorMat;
	// this.errorMat = new Mat();
	// Core.absdiff(originalMat, originalWithErrorMat, this.errorMat);
	//
	// // Save the original, original with error and the error into each color
	// layer
	// List<Mat> listMat = Arrays.asList(this.originalMat,
	// this.originalWithError, this.errorMat);
	// Mat mergedMat = new Mat(originalMat.size(), CvType.CV_8UC3);
	// Core.merge(listMat, mergedMat);
	// this.mergedMat.set(mergedMat);
	// }

	public ErrorEntry( Path storagePath, Mat originalMat, Mat originalWithErrorMat, Mat errorMat, String errorClass, String errorName )
	{
		this.storagePath = storagePath;

		this.originalMat = originalMat;
		this.errorMat = errorMat;
		this.originalWithError = originalWithErrorMat;

		this.errorClass.set( errorClass );
		this.errorName.set( errorName );
	}

	// /**
	// * Reading ErrorEntry CTOR
	// *
	// * @param storagePath
	// */
	// public ErrorEntry(Path storagePath) {
	// this.storagePath = storagePath;
	// this.initClassAndName(storagePath);
	//
	// Mat encodedImage = Images.toMat(storagePath);
	//
	// // split channels to extract GL and Error Mat
	// List<Mat> encodedImageChannelMats = new ArrayList<Mat>();
	// Core.split(encodedImage, encodedImageChannelMats);
	//
	// this.originalMat = encodedImageChannelMats.get(0);
	// this.originalWithError = encodedImageChannelMats.get(1);
	// this.errorMat = encodedImageChannelMats.get(2);
	// this.mergedMat.set(encodedImage);
	// }

	// /**
	// * Handle common code between the constructors
	// */
	// private void initClassAndName(Path storagePath) {
	// // Defining Pattern and Matcher
	// final Pattern errorClassPattern = Pattern.compile("[a-zA-Z]+");
	// final String classAndName = storagePath.getFileName().toString();
	// final Matcher m = errorClassPattern.matcher(classAndName);
	//
	// // Searching first instance
	// m.find();
	// String clazz = m.group();
	//
	// // Store
	// this.errorClass.set(clazz);
	// this.errorName.set(classAndName.replace(clazz, ""));
	// }

	// ==================================================
	// Getter Implementation
	// ==================================================
	// Properties
	public ReadOnlyStringProperty getErrorClassProperty()
	{
		return this.errorClass.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty getErrorNameProperty()
	{
		return this.errorName.getReadOnlyProperty();
	}
}
