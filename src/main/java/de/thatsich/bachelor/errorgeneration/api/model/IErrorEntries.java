package de.thatsich.bachelor.errorgeneration.api.model;

import de.thatsich.bachelor.errorgeneration.intern.control.error.ErrorEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorEntries
{
	public ListProperty<ErrorEntry> getErrorEntryListProperty();

	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty();

	public ErrorEntry getSelectedErrorEntry();

	public void setSelectedErrorEntry(ErrorEntry entry);
}
