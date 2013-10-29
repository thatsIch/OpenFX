package de.thatsich.bachelor.errorgeneration.restricted.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;

public class ErrorEntries implements IErrorEntries {

	// Properties
	final private ListProperty<ErrorEntry> errorEntries = new SimpleListProperty<ErrorEntry>(FXCollections.<ErrorEntry>observableArrayList());
	final private ObjectProperty<ErrorEntry> selectedErrorEntry = new SimpleObjectProperty<ErrorEntry>();
	
	// Property Getter
	public ListProperty<ErrorEntry> getErrorEntryListProperty() { return this.errorEntries; }
	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty() { return this.selectedErrorEntry; }
	
	// Getter
	public ErrorEntry getSelectedErrorEntry() { return this.selectedErrorEntry.get(); }
	
	// Setter
	public void setSelectedErrorEntry(ErrorEntry entry) { this.selectedErrorEntry.set(entry); }
}
