package de.thatsich.bachelor.errorgeneration.api.model;

import de.thatsich.bachelor.errorgeneration.intern.control.error.core.ErrorEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorEntries
{
	ListProperty<ErrorEntry> errorEntries();

	ObjectProperty<ErrorEntry> selectedErrorEntry();
}
