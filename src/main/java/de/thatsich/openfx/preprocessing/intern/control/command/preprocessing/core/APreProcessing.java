package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core;

import com.google.inject.Inject;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.core.Log;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import java.nio.file.Path;


public abstract class APreProcessing implements IPreProcessing
{
	// Properties
	private final PreProcessorConfiguration config;

	// Injects
	@Inject
	protected Log log;

	/**
	 * CTOR
	 */
	protected APreProcessing(PreProcessorConfiguration config)
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
