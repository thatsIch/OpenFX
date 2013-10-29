package de.thatsich.bachelor.errorgeneration.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;

public interface IErrorEntries {
	public ListProperty<ErrorEntry> getErrorEntryListProperty();
	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty();
	public ErrorEntry getSelectedErrorEntry();
	public void setSelectedErrorEntry(ErrorEntry entry);
}
