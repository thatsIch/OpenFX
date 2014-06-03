package de.thatsich.openfx.prediction.intern.model;

import de.thatsich.openfx.prediction.api.model.IPredictionState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class PredictionState implements IPredictionState
{
	// Properties
	private final ObjectProperty<Path> predictionFolderPath = new SimpleObjectProperty<>();

	// Property Getter
	public ObjectProperty<Path> getPredictionFolderPathProperty()
	{ return this.predictionFolderPath; }

	// Getter
	public Path getPredictionFolderPath()
	{ return this.predictionFolderPath.get(); }

	// Setter
	public void setPredictionFolderPath(Path predictionFolderPath)
	{ this.predictionFolderPath.set(predictionFolderPath); }
}
