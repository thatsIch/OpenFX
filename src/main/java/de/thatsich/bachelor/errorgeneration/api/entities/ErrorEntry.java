package de.thatsich.bachelor.errorgeneration.api.entities;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import org.opencv.core.Mat;

import java.nio.file.Path;


public class ErrorEntry
{
	private final Mat originalMat;
	private final Mat errorMat;
	private final Mat originalWithErrorMat;
	private final ReadOnlyStringWrapper errorClass = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper errorName = new ReadOnlyStringWrapper();
	private final Path storagePath;

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

	public ErrorEntry(Path storagePath, Mat originalMat, Mat originalWithErrorMat, Mat errorMat, String errorClass, String errorName)
	{
		this.storagePath = storagePath;

		this.originalMat = originalMat;
		this.errorMat = errorMat;
		this.originalWithErrorMat = originalWithErrorMat;

		this.errorClass.set(errorClass);
		this.errorName.set(errorName);
	}

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

	public Mat getOriginalMat() {
		return this.originalMat;
	}

	public Mat getErrorMat() { return this.errorMat;}

	public Mat getOriginalWithErrorMat() { return this.originalWithErrorMat; }

	public Path getStoragePath() { return this.storagePath; }
}
