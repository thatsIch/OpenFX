package de.thatsich.openfx.errorgeneration.intern.model;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class Errors implements IErrors
{
	// Properties
	final private ListProperty<IError> errorEntries = new SimpleListProperty<>(FXCollections.<IError>observableArrayList());
	final private ObjectProperty<IError> selectedErrorEntry = new SimpleObjectProperty<>();

	// Property Getter
	@Override
	public ListProperty<IError> list()
	{
		return this.errorEntries;
	}

	@Override
	public ObjectProperty<IError> selected()
	{
		return this.selectedErrorEntry;
	}

}
