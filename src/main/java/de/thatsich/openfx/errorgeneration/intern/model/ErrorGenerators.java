package de.thatsich.openfx.errorgeneration.intern.model;

import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class ErrorGenerators implements IErrorGenerators
{
	// Properties
	final private ListProperty<IErrorGenerator> errorGeneratorList = new SimpleListProperty<>(FXCollections.<IErrorGenerator>observableArrayList());
	final private ObjectProperty<IErrorGenerator> selectedErrorGenerator = new SimpleObjectProperty<>();

	// Getter
	public ListProperty<IErrorGenerator> errorGenerators()
	{
		return this.errorGeneratorList;
	}

	public ObjectProperty<IErrorGenerator> selectedErrorGenerator()
	{
		return this.selectedErrorGenerator;
	}

}
