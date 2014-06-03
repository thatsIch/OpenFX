package de.thatsich.openfx.errorgeneration.api.model;

import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorEntries
{
	ListProperty<ErrorEntry> errorEntries();

	ObjectProperty<ErrorEntry> selectedErrorEntry();
}
