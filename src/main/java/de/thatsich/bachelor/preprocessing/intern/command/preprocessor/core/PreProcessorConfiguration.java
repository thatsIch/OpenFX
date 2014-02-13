package de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;


public class PreProcessorConfiguration
{
	// Properties
	private final ReadOnlyObjectWrapper<Path>	path				= new ReadOnlyObjectWrapper<Path>();
	private final ReadOnlyStringWrapper			preProcessingName	= new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper		inputSize			= new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper		outputSize			= new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper			id					= new ReadOnlyStringWrapper();

	// CTOR
	public PreProcessorConfiguration( Path path, String preProcessingName, int inputSize, int outputSize, String id )
	{
		this.path.set( path );
		this.preProcessingName.set( preProcessingName );
		this.inputSize.set( inputSize );
		this.outputSize.set( outputSize );
		this.id.set( id );
	}

	// Property Getter
	public ReadOnlyObjectProperty<Path> getFilePathProperty()
	{
		return this.path.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty getPreProcessorNameProperty()
	{
		return this.preProcessingName.getReadOnlyProperty();
	}

	public ReadOnlyIntegerProperty getInputSizeProperty()
	{
		return this.inputSize.getReadOnlyProperty();
	}

	public ReadOnlyIntegerProperty getOutputSizeProperty()
	{
		return this.outputSize.getReadOnlyProperty();
	}

	public ReadOnlyStringProperty getIDProperty()
	{
		return this.id.getReadOnlyProperty();
	}
}
