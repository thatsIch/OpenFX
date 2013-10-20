package de.thatsich.bachelor.errorgeneration.restricted.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.core.opencv.IErrorGenerator;

public class ErrorGenerators {

	// Properties
	final private ListProperty<IErrorGenerator> errorGeneratorList = new SimpleListProperty<IErrorGenerator>(FXCollections.<IErrorGenerator>observableArrayList());
	final private ObjectProperty<IErrorGenerator> selectedErrorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	
	// Getter
	public ListProperty<IErrorGenerator> getErrorGeneratorListProperty() { return this.errorGeneratorList; }
	public ObjectProperty<IErrorGenerator> getSelectedErrorGeneratorProperty() { return this.selectedErrorGenerator; }
}
