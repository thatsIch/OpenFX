package de.thatsich.openfx.classification.intern.model;

import de.thatsich.openfx.classification.api.model.IClassificationState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

public class ClassificationState implements IClassificationState
{
	// Properties
	private final ObjectProperty<Path> binaryClassifierFolderPath = new SimpleObjectProperty<>();

	// Property Getter
	public ObjectProperty<Path> path()
	{ return this.binaryClassifierFolderPath; }

}
