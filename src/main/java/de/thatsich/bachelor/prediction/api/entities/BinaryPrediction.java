package de.thatsich.bachelor.prediction.api.entities;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import org.opencv.core.Mat;

public class BinaryPrediction {

	// Properties
	private final ReadOnlyObjectWrapper<Path> filePath = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> withError = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> errorIndication = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> errorPrediction = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyStringWrapper classifierName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper errorClassName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();
	
	public BinaryPrediction(
			Path filePath,
			Mat withError,
			Mat errorIndication,
			Mat errorPrediction,
			String classifierName,
			String extractorName,
			int frameSize,
			String errorClassName,
			String id) {
		this.filePath.set(filePath);
		this.withError.set(withError);
		this.errorIndication.set(errorIndication);
		this.errorPrediction.set(errorPrediction);
		this.classifierName.set(classifierName);
		this.extractorName.set(extractorName);
		this.frameSize.set(frameSize);
		this.errorClassName.set(errorClassName);
		this.id.set(id);
	}

	// Property Getters
	public ReadOnlyObjectProperty<Path> getFilePathProperty() { return this.filePath.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<Mat> getWithErrorProperty() { return this.withError.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<Mat> getErrorIndicationProperty() { return this.errorIndication.getReadOnlyProperty(); }
	public ReadOnlyObjectProperty<Mat> getErrorPredictionProperty() { return this.errorPrediction.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getClassifierNameProperty() { return this.classifierName.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getExtractorNameProperty() { return this.extractorName.getReadOnlyProperty(); }
	public ReadOnlyIntegerProperty getFrameSizeProperty() { return this.frameSize.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getErrorClassNameProperty() { return this.errorClassName.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getIDProperty() { return this.id.getReadOnlyProperty(); }
}
