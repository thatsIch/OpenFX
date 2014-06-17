package de.thatsich.openfx.classification.intern.control.classification.core;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyStringProperty;


public abstract class ATraindBinaryClassifier implements ITrainedBinaryClassifier
{
	// Properties
	private final BinaryClassificationConfig config;

	// Injects
	@Inject protected Log log;

	/**
	 * CTOR
	 *
	 * @param config Configuration of the BinaryClassifier (FilePath,
	 *               ClassificaationName, ExtractorName, FrameSize, ErrorName, ID)
	 */
	protected ATraindBinaryClassifier(BinaryClassificationConfig config)
	{
		this.config = config;
	}

	// Getter
	@Override
	public String getName()
	{
		return this.getClass().getSimpleName();
	}

	@Override
	public ReadOnlyStringProperty classificationName()
	{
		return this.config.classificationName;
	}

	@Override
	public ReadOnlyStringProperty extractorName()
	{
		return this.config.extractorName;
	}

	@Override
	public ReadOnlyIntegerProperty tileSize()
	{
		return this.config.tileSize;
	}

	@Override
	public ReadOnlyStringProperty errorName()
	{
		return this.config.errorName;
	}

	@Override
	public ReadOnlyStringProperty idProperty()
	{
		return this.config.id;
	}

	@Override
	public BinaryClassificationConfig getConfig()
	{
		return this.config;
	}

	@Override
	public ReadOnlyLongProperty trainTime() { return this.config.trainTime; }
}
