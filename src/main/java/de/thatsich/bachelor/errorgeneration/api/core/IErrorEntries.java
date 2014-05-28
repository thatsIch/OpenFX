package de.thatsich.bachelor.errorgeneration.api.core;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorEntries
{
	public ListProperty<ErrorEntry> getErrorEntryListProperty();

	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty();

	public ErrorEntry getSelectedErrorEntry();

	public void setSelectedErrorEntry(ErrorEntry entry);
}
