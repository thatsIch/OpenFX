package de.thatsich.bachelor.featureextraction.intern.models;

import de.thatsich.bachelor.featureextraction.api.model.IFeatureState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class FeatureState implements IFeatureState
{
	// Properties
	private final ObjectProperty<Path> featureVectorFolderPath = new SimpleObjectProperty<>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();

	// Property Getter
	public ObjectProperty<Path> getFeatureVectorFolderPathProperty()
	{ return this.featureVectorFolderPath; }

	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }

	// Getter
	public Path getFeatureVectorFolderPath()
	{ return this.featureVectorFolderPath.get(); }

	// Setter
	public void setFeatureVectorFolderPath(Path featureVectorFolderPath)
	{ this.featureVectorFolderPath.set(featureVectorFolderPath); }

	public int getFrameSize() { return this.frameSize.get(); }

	public void setFrameSize(int frameSize) { this.frameSize.set(frameSize); }
}
