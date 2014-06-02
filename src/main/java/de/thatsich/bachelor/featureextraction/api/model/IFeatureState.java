package de.thatsich.bachelor.featureextraction.api.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IFeatureState
{
	ObjectProperty<Path> path();

	IntegerProperty frameSize();
}
