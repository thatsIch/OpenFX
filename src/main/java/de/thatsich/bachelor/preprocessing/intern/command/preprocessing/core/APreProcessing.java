package de.thatsich.bachelor.preprocessing.intern.command.preprocessing.core;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import com.google.inject.Inject;

import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.core.Log;


public abstract class APreProcessing implements IPreProcessing
{
	// Properties
	private final PreProcessorConfiguration	config;

	// Injects
	@Inject protected Log					log;

	/**
	 * CTOR
	 */
	protected APreProcessing( PreProcessorConfiguration config )
	{
		this.config = config;
	}

	// Getter
	@Override
	public String getName()
	{
		return this.getClass().getSimpleName();
	}

	// Property Getter
	@Override
	public ReadOnlyObjectProperty<Path> getFilePathProperty()
	{
		return this.config.getFilePathProperty();
	}

	@Override
	public ReadOnlyStringProperty getPreProcessingNameProperty()
	{
		return this.config.getPreProcessorNameProperty();
	}

	@Override
	public ReadOnlyIntegerProperty getInputSizeProperty()
	{
		return this.config.getInputSizeProperty();
	}

	@Override
	public ReadOnlyIntegerProperty getOutputSizeProperty()
	{
		return this.config.getOutputSizeProperty();
	}

	@Override
	public ReadOnlyStringProperty getIdProperty()
	{
		return this.config.getIDProperty();
	}
}
