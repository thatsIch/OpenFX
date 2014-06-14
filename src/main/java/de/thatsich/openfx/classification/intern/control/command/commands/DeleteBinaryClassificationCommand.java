package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;

public class DeleteBinaryClassificationCommand extends ACommand<ITrainedBinaryClassifier>
{
	final private ITrainedBinaryClassifier binaryClassification;
	private final ClassificationFileStorageService storage;

	@Inject
	public DeleteBinaryClassificationCommand(@Assisted ITrainedBinaryClassifier binaryClassification, ClassificationFileStorageService storage)
	{
		this.binaryClassification = binaryClassification;
		this.storage = storage;
	}

	@Override
	protected ITrainedBinaryClassifier call() throws Exception
	{
		this.storage.delete(this.binaryClassification);

		return this.binaryClassification;
	}
}