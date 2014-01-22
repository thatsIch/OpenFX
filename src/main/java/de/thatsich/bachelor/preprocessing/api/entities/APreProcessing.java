package de.thatsich.bachelor.preprocessing.api.entities;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.intern.command.classifier.BinaryClassifierConfiguration;
import de.thatsich.core.Log;


public abstract class APreProcessing implements IPreProcessing
{
	// Properties
	private final ReadOnlyObjectWrapper<BinaryClassifierConfiguration>	config	= new ReadOnlyObjectWrapper<>();

	// Injects
	@Inject
	protected Log														log;

	/**
	 * CTOR
	 * 
	 * @param config
	 *            Configuration of the BinaryClassifier (FilePath,
	 *            ClassificaationName, ExtractorName, FrameSize, ErrorName, ID)
	 */
	protected APreProcessing( BinaryClassifierConfiguration config )
	{
		this.config.set( config );
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
		return this.config.get().getFilePath();
	}
	@Override
	public ReadOnlyStringProperty getClassificationNameProperty()
	{
		return this.config.get().getClassificationName();
	}
	@Override
	public ReadOnlyStringProperty getExtractorNameProperty()
	{
		return this.config.get().getExtractorName();
	}
	@Override
	public ReadOnlyIntegerProperty getFrameSizeProperty()
	{
		return this.config.get().getFrameSize();
	}
	@Override
	public ReadOnlyStringProperty getErrorNameProperty()
	{
		return this.config.get().getErrorName();
	}
	@Override
	public ReadOnlyStringProperty getIdProperty()
	{
		return this.config.get().getId();
	}
}
