package de.thatsich.openfx.featureextraction.intern.model;

import com.google.inject.Singleton;
import de.thatsich.openfx.featureextraction.api.model.IFeatureState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

@Singleton
public class FeatureState implements IFeatureState
{
	// Properties
	private final ObjectProperty<Path> featureVectorFolderPath = new SimpleObjectProperty<>();
	private final IntegerProperty frameSize = new SimpleIntegerProperty();

	// Property Getter
	@Override
	public ObjectProperty<Path> path()
	{
		return this.featureVectorFolderPath;
	}

	@Override
	public IntegerProperty frameSize()
	{
		return this.frameSize;
	}
}
