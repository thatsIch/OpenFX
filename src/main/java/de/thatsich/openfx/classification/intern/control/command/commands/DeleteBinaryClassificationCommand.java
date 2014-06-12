package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.ITraindBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;

public class DeleteBinaryClassificationCommand extends ACommand<ITraindBinaryClassifier>
{
	final private ITraindBinaryClassifier binaryClassification;
	private final ClassificationFileStorageService storage;

	@Inject
	public DeleteBinaryClassificationCommand(@Assisted ITraindBinaryClassifier binaryClassification, ClassificationFileStorageService storage)
	{
		this.binaryClassification = binaryClassification;
		this.storage = storage;
	}

	@Override
	protected ITraindBinaryClassifier call() throws Exception
	{
		this.storage.delete(this.binaryClassification);

		return this.binaryClassification;
	}
}