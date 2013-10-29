package de.thatsich.bachelor.featureextraction.restricted.models;

import java.nio.file.Path;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class FeatureState implements IFeatureState {
	// Properties
	private final ObjectProperty<Path> featureVectorFolderPath = new SimpleObjectProperty<Path>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();
	
	// Property Getter
	public ObjectProperty<Path> getFeatureVectorFolderPathProperty() { return this.featureVectorFolderPath; }
	public IntegerProperty getFrameSizeProperty() { return this.frameSize; }
	
	// Getter
	public Path getFeatureVectorFolderPath() { return this.featureVectorFolderPath.get(); }
	public int getFrameSize() { return this.frameSize.get(); }
	
	// Setter
	public void setFeatureVectorFolderPath(Path featureVectorFolderPath) { this.featureVectorFolderPath.set(featureVectorFolderPath); }
	public void setFrameSize(int frameSize) { this.frameSize.set(frameSize); }
}
