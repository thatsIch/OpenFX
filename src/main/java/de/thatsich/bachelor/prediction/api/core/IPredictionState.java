package de.thatsich.bachelor.prediction.api.core;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;

public interface IPredictionState {
	// Property Getter
	public ObjectProperty<Path> getPredictionFolderPathProperty();
	
	// Getter
	public Path getPredictionFolderPath();
	
	// Setter
	public void setPredictionFolderPath(Path predictionFolderPath);
}
