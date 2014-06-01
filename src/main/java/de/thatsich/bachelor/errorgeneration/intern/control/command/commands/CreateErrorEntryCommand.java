package de.thatsich.bachelor.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.errorgeneration.intern.control.error.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.intern.control.command.service.ErrorFactoryService;
import de.thatsich.bachelor.errorgeneration.intern.control.command.service.ErrorStorageService;
import de.thatsich.core.javafx.ACommand;
import org.opencv.core.Mat;

import java.nio.file.Path;

public class CreateErrorEntryCommand extends ACommand<ErrorEntry>
{
	// Fields
	final private Mat imageMat;
	final private Path imagePath;
	final private IErrorGenerator generator;

	// Injections
	@Inject private ErrorFactoryService factory;
	@Inject private ErrorStorageService storage;

	@Inject
	public CreateErrorEntryCommand(@Assisted Mat imageMat, @Assisted Path imagePath, @Assisted IErrorGenerator generator)
	{
		this.imageMat = imageMat;
		this.imagePath = imagePath;
		this.generator = generator;
	}

	@Override
	protected ErrorEntry call() throws Exception
	{
		Mat copy = this.imageMat.clone();
		copy = this.generator.generateError(copy);
		this.log.info("Error generated.");

		final ErrorEntry entry = this.factory.getErrorEntryFromMat(this.imagePath, this.imageMat, copy);

		// store created error
		this.storage.store(entry);

		return entry;
	}
}
