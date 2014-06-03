package de.thatsich.openfx.classification.intern.control.classifier.core;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.nio.file.Path;

public class BinaryClassifierConfiguration
{
	// Properties
	private final ReadOnlyObjectWrapper<Path> filePath = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyStringWrapper classificationName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper errorName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();

	// CTOR
	public BinaryClassifierConfiguration(Path filePath, String classificationName, String extractorName, int frameSize, String errorName, String id)
	{
		this.filePath.set(filePath);
		this.classificationName.set(classificationName);
		this.extractorName.set(extractorName);
		this.frameSize.set(frameSize);
		this.errorName.set(errorName);
		this.id.set(id);
	}

	// Property Getter
	public ReadOnlyObjectProperty<Path> getFilePath()
	{ return this.filePath.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getClassificationName() { return classificationName.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getExtractorName() { return extractorName.getReadOnlyProperty(); }

	public ReadOnlyIntegerProperty getFrameSize() { return frameSize.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getErrorName() { return errorName.getReadOnlyProperty(); }

	public ReadOnlyStringProperty getId() { return id.getReadOnlyProperty(); }
}
