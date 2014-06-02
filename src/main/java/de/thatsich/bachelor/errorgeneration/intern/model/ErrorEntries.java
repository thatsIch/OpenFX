package de.thatsich.bachelor.errorgeneration.intern.model;

import de.thatsich.bachelor.errorgeneration.api.model.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.intern.control.error.core.ErrorEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

public class ErrorEntries implements IErrorEntries
{
	// Properties
	final private ListProperty<ErrorEntry> errorEntries = new SimpleListProperty<>(FXCollections.<ErrorEntry>observableArrayList());
	final private ObjectProperty<ErrorEntry> selectedErrorEntry = new SimpleObjectProperty<>();

	// Property Getter
	public ListProperty<ErrorEntry> errorEntries()
	{
		return this.errorEntries;
	}

	public ObjectProperty<ErrorEntry> selectedErrorEntry()
	{
		return this.selectedErrorEntry;
	}

}
