package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorStorageService;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreateErrorEntryCommand extends ACommand<ErrorEntry>
{
	// Fields
	final private Mat imageMat;
	final private Path errorFolder;
	private final String errorClass;
	private final String errorID;
	final private IErrorGenerator generator;
	final private ErrorStorageService storage;

	@Inject
	public CreateErrorEntryCommand(@Assisted Path errorFolder, @Assisted String errorClass, @Assisted Mat imageMat, @Assisted IErrorGenerator generator, final ErrorStorageService storage)
	{
		this.imageMat = imageMat;
		this.errorFolder = errorFolder;
		this.errorClass = errorClass;
		this.errorID = UUID.randomUUID().toString();
		this.generator = generator;
		this.storage = storage;
	}

	@Override
	protected ErrorEntry call() throws Exception
	{
		Mat copy = this.imageMat.clone();
		copy = this.generator.generateError(copy);
		this.log.info("Error generated.");

		final String fileName = this.getFileName();
		final Path errorPath = this.errorFolder.resolve(fileName);
		this.log.info("Errorpath specified " + errorPath);

		final ErrorEntry entry = this.matToErrorEntry(errorPath, this.imageMat, copy);

		// store created error
		this.storage.save(entry);

		return entry;
	}

	private String getFileName()
	{
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
		final String date = format.format(new Date());
		final String errorClass = this.errorClass;

		return date + "_" + errorClass + "_" + this.errorID;
	}

	private ErrorEntry matToErrorEntry(Path filePath, Mat originalMat, Mat originalWithErrorMat)
	{
		final Mat onlyErrorMat = new Mat();
		Core.absdiff(originalMat, originalWithErrorMat, onlyErrorMat);

		return new ErrorEntry(filePath, originalMat, originalWithErrorMat, onlyErrorMat, this.errorClass, this.errorID);
	}
}
