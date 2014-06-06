package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;


public abstract class APreProcessing implements IPreProcessing
{
	// Properties
	private final PreProcessingConfig config;

	// Injects
	@Inject protected Log log;

	/**
	 * CTOR
	 */
	protected APreProcessing(PreProcessingConfig config)
	{
		this.config = config;
	}

	@Override
	public ReadOnlyStringProperty nameProperty()
	{
		return this.config.name;
	}

	@Override
	public ReadOnlyIntegerProperty inputSizeProperty()
	{
		return this.config.inputSize;
	}

	@Override
	public ReadOnlyIntegerProperty outputSizeProperty()
	{
		return this.config.outputSize;
	}

	@Override
	public ReadOnlyStringProperty idProperty()
	{
		return this.config.id;
	}

	@Override
	public PreProcessingConfig getConfig()
	{
		return this.config;
	}
}
