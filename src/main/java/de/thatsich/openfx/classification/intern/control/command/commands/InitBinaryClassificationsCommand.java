package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.ITraindBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;

import java.util.List;


public class InitBinaryClassificationsCommand extends ACommand<List<ITraindBinaryClassifier>>
{
	private final ClassificationFileStorageService storage;

	@Inject
	protected InitBinaryClassificationsCommand(ClassificationFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<ITraindBinaryClassifier> call() throws Exception
	{
		return this.storage.init();
	}
}
