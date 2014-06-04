package de.thatsich.openfx.imageprocessing.api.model;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;

public interface IImageState
{
	ObjectProperty<Path> imageFolder();

	ObjectProperty<Path> lastLocation();
}
