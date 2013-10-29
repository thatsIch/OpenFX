package de.thatsich.bachelor.prediction.api.entities;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import org.opencv.core.Mat;

public class BinaryPrediction {

	private final ReadOnlyObjectWrapper<Path> filePath = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> withError = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> errorIndication = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> errorPrediction = new ReadOnlyObjectWrapper<>();
	
	public BinaryPrediction(
			Path filePath,
			Mat withError,
			Mat errorIndication,
			Mat errorPrediction) {
		this.filePath.set(filePath);
		this.withError.set(withError);
		this.errorIndication.set(errorIndication);
		this.errorPrediction.set(errorPrediction);
	}

	// Getters
	public ReadOnlyObjectProperty<Path> getFilePathProperty() { return this.filePath.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<Mat> getWithErrorProperty() { return this.withError.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<Mat> getErrorIndicationProperty() { return this.errorIndication.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<Mat> getErrorPredictionProperty() { return this.errorPrediction.getReadOnlyProperty(); }	
}
