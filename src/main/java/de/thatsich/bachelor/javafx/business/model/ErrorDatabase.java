package de.thatsich.bachelor.javafx.business.model;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.opencv.IErrorGenerator;

public class ErrorDatabase {
	
	// Properties
	final private ObjectProperty<Path> errorEntryFolderPath = new SimpleObjectProperty<Path>();
	final private ListProperty<IErrorGenerator> errorGenerators = new SimpleListProperty<IErrorGenerator>(FXCollections.<IErrorGenerator>observableArrayList());
	final private ObjectProperty<IErrorGenerator> errorGenerator = new SimpleObjectProperty<IErrorGenerator>();
	final private ListProperty<ErrorEntry> errorEntries = new SimpleListProperty<ErrorEntry>(FXCollections.<ErrorEntry>observableArrayList());
	final private ObjectProperty<ErrorEntry> errorEntry = new SimpleObjectProperty<ErrorEntry>();
	final private IntegerProperty errorLoopCount = new SimpleIntegerProperty();
	
	// ==================================================
	// Property Implementation
	// ==================================================
	public ObjectProperty<Path> getErrorEntryFolderPath() { return this.errorEntryFolderPath; }
	public ListProperty<IErrorGenerator> getErrorGeneratorListProperty() { return this.errorGenerators; }
	public ObjectProperty<IErrorGenerator> getSelectedErrorGeneratorProperty() { return this.errorGenerator; }
	public ListProperty<ErrorEntry> getErrorEntryListProperty() { return this.errorEntries; }
	public ObjectProperty<ErrorEntry> getSelectedErrorEntryProperty() { return this.errorEntry; }
	public IntegerProperty getErrorLoopCountProperty() { return this.errorLoopCount; }
}
