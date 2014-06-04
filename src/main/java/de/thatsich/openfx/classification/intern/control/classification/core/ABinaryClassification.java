package de.thatsich.openfx.classification.intern.control.classification.core;

import com.google.inject.Inject;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.core.Log;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;

import java.nio.file.Path;


public abstract class ABinaryClassification implements IBinaryClassification
{
	// Properties
	private final ReadOnlyObjectWrapper<BinaryClassifierConfiguration> config = new ReadOnlyObjectWrapper<>();

	// Injects
	@Inject
	protected Log log;

	/**
	 * CTOR
	 *
	 * @param config Configuration of the BinaryClassifier (FilePath,
	 *               ClassificaationName, ExtractorName, FrameSize, ErrorName, ID)
	 */
	protected ABinaryClassification(BinaryClassifierConfiguration config)
	{
		this.config.set(config);
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