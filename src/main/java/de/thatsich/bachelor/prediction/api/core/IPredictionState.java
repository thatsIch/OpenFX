package de.thatsich.bachelor.prediction.api.core;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IPredictionState
{
	// Property Getter
	public ObjectProperty<Path> getPredictionFolderPathProperty();

	// Getter
	public Path getPredictionFolderPath();

	// Setter
	public void setPredictionFolderPath(Path predictionFolderPath);
}
