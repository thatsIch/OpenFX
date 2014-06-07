package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageFileStorageService;
import de.thatsich.openfx.imageprocessing.intern.control.entity.Image;
import de.thatsich.openfx.imageprocessing.intern.control.entity.ImageConfig;
import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.file.Path;

public class CreateImageCommand extends ACommand<IImage>
{

	// Properties
	private final Path originPath;
	private final ImageFileStorageService storage;

	@AssistedInject
	public CreateImageCommand(@Assisted("origin") Path originPath, ImageFileStorageService storage)
	{
		this.originPath = originPath;
		this.storage = storage;
	}

	@Override
	protected IImage call() throws IOException
	{
		final String name = this.originPath.getFileName().toString();
		final Mat mat = Images.toMat(this.originPath);
		final ImageConfig config = new ImageConfig(name);
		final IImage original = new Image(config, mat);

		final IImage copy = this.storage.create(original);

		if (copy == null)
		{
			this.cancel();
		}

		return copy;
	}
}
