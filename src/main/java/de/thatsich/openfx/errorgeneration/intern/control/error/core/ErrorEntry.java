package de.thatsich.openfx.errorgeneration.intern.control.error.core;

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
	private final ReadOnlyStringWrapper errorID = new ReadOnlyStringWrapper();
	private final Path storagePath;

	public ErrorEntry(Path storagePath, Mat originalMat, Mat originalWithErrorMat, Mat errorMat, String errorClass, String errorID)
	{
		this.storagePath = storagePath;

		this.originalMat = originalMat;
		this.errorMat = errorMat;
		this.originalWithErrorMat = originalWithErrorMat;

		this.errorClass.set(errorClass);
		this.errorID.set(errorID);
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
		return this.errorID.getReadOnlyProperty();
	}

	public Mat getOriginalMat()
	{
		return this.originalMat;
	}

	public Mat getErrorMat() { return this.errorMat;}

	public Mat getOriginalWithErrorMat() { return this.originalWithErrorMat; }

	public Path path() { return this.storagePath; }
}
