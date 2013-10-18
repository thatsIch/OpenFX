package de.thatsich.bachelor.javafx.business.model;

import com.google.inject.Singleton;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;

@Singleton
public class ErrorEntryList {

	// Properties
	final private ListProperty<ErrorEntry> errorEntries = new SimpleListProperty<ErrorEntry>(FXCollections.<ErrorEntry>observableArrayList());
	final private ObjectProperty<ErrorEntry> errorEntry = new SimpleObjectProperty<ErrorEntry>();
	
	// Property Getter
	public ListProperty<ErrorEntry> getErrorEntryListProperty() { return this.errorEntries; }
	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty() { return this.errorEntry; }
}
