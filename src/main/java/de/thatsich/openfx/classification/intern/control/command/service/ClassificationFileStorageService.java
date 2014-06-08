package de.thatsich.openfx.classification.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.classification.api.model.IClassificationState;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 07.06.2014.
 */
public class ClassificationFileStorageService extends AFileStorageService<IBinaryClassification>
{
	@Inject
	protected ClassificationFileStorageService(IClassificationState state)
	{
		super(state.path().get());
	}

	@Override
	public IBinaryClassification create(IBinaryClassification elem) throws IOException
	{
		return null;
	}

	@Override
	public IBinaryClassification retrieve(Path path) throws IOException
	{
		return null;
	}

	@Override
	public IBinaryClassification update(IBinaryClassification elem) throws IOException
	{
		return null;
	}

	@Override
	public void delete(IBinaryClassification elem) throws IOException
	{

	}
}
