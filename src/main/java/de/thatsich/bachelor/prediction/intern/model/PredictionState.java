package de.thatsich.bachelor.prediction.intern.model;

import java.nio.file.Path;

import de.thatsich.bachelor.prediction.api.core.IPredictionState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PredictionState implements IPredictionState {
	// Properties
	private final ObjectProperty<Path> predictionFolderPath = new SimpleObjectProperty<>();
	
	// Property Getter
	public ObjectProperty<Path> getPredictionFolderPathProperty() { return this.predictionFolderPath; }
	
	// Getter
	public Path getPredictionFolderPath() { return this.predictionFolderPath.get(); }
	
	// Setter
	public void setPredictionFolderPath(Path predictionFolderPath) { this.predictionFolderPath.set(predictionFolderPath); }
}
