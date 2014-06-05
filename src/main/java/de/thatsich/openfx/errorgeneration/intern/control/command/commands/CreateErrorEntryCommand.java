package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.errorgeneration.intern.control.entity.Error;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreateErrorEntryCommand extends ACommand<IError>
{
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	// Fields
	private final Mat imageMat;
	private final String errorClass;
	private final IErrorGenerator generator;
	private final ErrorFileStorageService storage;

	@Inject
	public CreateErrorEntryCommand(@Assisted String errorClass, @Assisted Mat imageMat, @Assisted IErrorGenerator generator, final ErrorFileStorageService storage)
	{
		this.imageMat = imageMat;
		this.errorClass = errorClass;
		this.generator = generator;
		this.storage = storage;
	}

	@Override
	protected IError call() throws Exception
	{
		Mat copy = this.imageMat.clone();
		copy = this.generator.generateError(copy);
		this.log.info("Error generated.");

		final IError entry = this.matToErrorEntry(this.imageMat, copy);

		// store created error
		this.storage.save(entry);

		return entry;
	}

	private IError matToErrorEntry(Mat original, Mat modified)
	{
		final Mat error = new Mat();
		Core.absdiff(original, modified, error);
		final String creationTime = format.format(new Date());
		final String id = UUID.randomUUID().toString();

		final ErrorConfig config = new ErrorConfig(creationTime, this.errorClass, id);
		return new Error(config, original, modified, error);
	}
}
