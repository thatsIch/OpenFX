package de.thatsich.openfx.errorgeneration.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.errorgeneration.intern.control.entity.Error;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CreateErrorCommand extends ACommand<IError>
{
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	// Fields
	private final Mat imageMat;
	private final String errorClass;
	private final IErrorGenerator generator;
	private final boolean smooth;
	private final boolean threshold;
	private final boolean denoising;
	private final ErrorFileStorageService storage;

	@Inject
	public CreateErrorCommand(@Assisted String errorClass, @Assisted Mat imageMat, @Assisted IErrorGenerator generator, @Assisted("smooth") boolean smooth, @Assisted("threshold") boolean threshold, @Assisted("denoising") boolean denoising, final ErrorFileStorageService storage)
	{
		this.imageMat = imageMat;
		this.errorClass = errorClass;
		this.generator = generator;
		this.smooth = smooth;
		this.threshold = threshold;
		this.denoising = denoising;
		this.storage = storage;
	}

	@Override
	protected IError call() throws Exception
	{
		final Mat blank = Mat.zeros(this.imageMat.size(), this.imageMat.type());
		final Mat error = this.generator.generateError(blank);
		this.log.info("Error generated.");

		final IError entry = this.matToErrorEntry(this.imageMat, error);
		final Mat modified = entry.modifiedProperty().get();

		if (this.smooth)
		{
			final Mat dst = Mat.zeros(error.size(), error.type());
			Imgproc.adaptiveBilateralFilter(modified, dst, new Size(11, 11), 22);
			dst.copyTo(modified);
		}

		if (this.threshold)
		{
			Imgproc.threshold(modified, modified, 0, 255, Imgproc.THRESH_OTSU);
		}

		if (this.denoising)
		{
			Photo.fastNlMeansDenoising(modified, modified);
		}

		// store created error
		this.storage.create(entry);

		return entry;
	}

	private IError matToErrorEntry(Mat original, Mat error)
	{
		final Mat modified = Mat.zeros(original.size(), original.type());

		for (int row = 0; row < original.rows(); row++)
		{
			for (int col = 0; col < original.cols(); col++)
			{
				final double originalValue = original.get(row, col)[0];
				final double errorValue = error.get(row, col)[0];

				if (errorValue > 0)
				{
					modified.put(row, col, errorValue);
				}
				else
				{
					modified.put(row, col, originalValue);
				}
			}
		}

		final String creationTime = format.format(new Date());
		final String id = UUID.randomUUID().toString();

		final ErrorConfig config = new ErrorConfig(creationTime, this.errorClass, id);
		return new Error(config, original, modified, error);
	}
}
