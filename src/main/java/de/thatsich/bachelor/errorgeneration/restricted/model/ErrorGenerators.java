package de.thatsich.bachelor.errorgeneration.restricted.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;

public class ErrorGenerators implements IErrorGenerators {

	// Properties
	final private ListProperty<IErrorGenerator> errorGeneratorList = new SimpleListProperty<IErrorGenerator>(FXCollections.<IErrorGenerator>observableArrayList());
	final private ObjectProperty<IErrorGenerator> selectedErrorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	
	// Getter
	public ListProperty<IErrorGenerator> getErrorGeneratorListProperty() { return this.errorGeneratorList; }
	public ObjectProperty<IErrorGenerator> getSelectedErrorGeneratorProperty() { return this.selectedErrorGenerator; }
}
