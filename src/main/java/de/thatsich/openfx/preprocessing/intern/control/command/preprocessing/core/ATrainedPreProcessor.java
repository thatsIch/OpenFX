package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;


public abstract class ATrainedPreProcessor implements ITrainedPreProcessor
{
	// Properties
	private final TrainedPreProcessorConfig config;

	// Injects
	@Inject protected Log log;

	/**
	 * CTOR
	 */
	protected ATrainedPreProcessor(TrainedPreProcessorConfig config)
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
	public TrainedPreProcessorConfig getConfig()
	{
		return this.config;
	}
}
