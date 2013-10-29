package de.thatsich.bachelor.featureextraction.api.core;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface IFeatureState {
	// Property Getter
	public ObjectProperty<Path> getFeatureVectorFolderPathProperty();
	public IntegerProperty getFrameSizeProperty();
	
	// Getter
	public Path getFeatureVectorFolderPath();
	public int getFrameSize();
	
	// Setter
	public void setFeatureVectorFolderPath(Path featureVectorFolderPath);
	public void setFrameSize(int frameSize);
}
