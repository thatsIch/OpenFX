package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;

import java.util.List;


public class InitBinaryClassificationsCommand extends ACommand<List<ITrainedBinaryClassifier>>
{
	private final ClassificationFileStorageService storage;

	@Inject
	protected InitBinaryClassificationsCommand(ClassificationFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<ITrainedBinaryClassifier> call() throws Exception
	{
		return this.storage.init();
	}
}
