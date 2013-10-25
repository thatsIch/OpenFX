package de.thatsich.bachelor.classificationtesting.restricted.models.state;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PredictionState {
	// Properties
	private final ObjectProperty<Path> predictionFolderPath = new SimpleObjectProperty<>();
	
	// Property Getter
	public ObjectProperty<Path> getPredictionFolderPathProperty() { return this.predictionFolderPath; }
}
