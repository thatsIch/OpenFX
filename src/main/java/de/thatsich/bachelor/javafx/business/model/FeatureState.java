package de.thatsich.bachelor.javafx.business.model;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FeatureState {
	// Properties
	private final ObjectProperty<Path> featureVectorFolderPath = new SimpleObjectProperty<Path>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	// Property Getter
	public ObjectProperty<Path> getFeatureVectorFolderPathProperty() { return this.featureVectorFolderPath; }
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
}
