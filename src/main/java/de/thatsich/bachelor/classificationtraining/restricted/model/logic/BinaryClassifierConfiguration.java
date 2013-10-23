package de.thatsich.bachelor.classificationtraining.restricted.model.logic;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class BinaryClassifierConfiguration {
	// Properties
	private final ReadOnlyStringWrapper classificationName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper errorName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();

	// CTOR
	public BinaryClassifierConfiguration(String classificationName, String extractorName, int frameSize, String errorName, String id) {
		this.classificationName.set(classificationName);
		this.extractorName.set(extractorName);
		this.frameSize.set(frameSize);
		this.errorName.set(errorName);
		this.id.set(id);
	}

	// Property Getter
	public ReadOnlyStringProperty getClassificationName() { return classificationName.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getExtractorName() { return extractorName.getReadOnlyProperty(); }
	public ReadOnlyIntegerProperty getFrameSize() { return frameSize.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getErrorName() { return errorName.getReadOnlyProperty(); }
	public ReadOnlyStringProperty getId() { return id.getReadOnlyProperty(); }
}
