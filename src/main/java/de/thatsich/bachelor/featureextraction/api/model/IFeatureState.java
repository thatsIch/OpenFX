package de.thatsich.bachelor.featureextraction.api.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IFeatureState
{
	// Property Getter
	public ObjectProperty<Path> getFeatureVectorFolderPathProperty();

	public IntegerProperty getFrameSizeProperty();

	// Getter
	public Path getFeatureVectorFolderPath();

	// Setter
	public void setFeatureVectorFolderPath(Path featureVectorFolderPath);

	public int getFrameSize();

	public void setFrameSize(int frameSize);
}
