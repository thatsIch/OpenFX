package de.thatsich.openfx.imageprocessing.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.openfx.imageprocessing.intern.control.entity.Image;
import de.thatsich.openfx.imageprocessing.intern.control.entity.ImageConfig;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageFileStorageService extends AFileStorageService<IImage>
{
	@Inject
	protected ImageFileStorageService(IImageState state)
	{
		super(state.imageFolder().get());
	}

	@Override
	public void save(final IImage elem) throws IOException
	{

	}

	@Override
	public IImage load(final Path path) throws IOException
	{
		final Path imagePath = path.toAbsolutePath();
		final Mat imageMat = Highgui.imread(imagePath.toString(), 0);
		final String fileName = path.getFileName().toString();
		final ImageConfig config = new ImageConfig(fileName);

		return new Image(config, imageMat);
	}

	@Override
	public void update(final IImage elem) throws IOException
	{

	}

	@Override
	public void delete(final IImage image) throws IOException
	{
		final String fileName = image.getImageName();
		final Path path = super.storagePath.resolve(fileName);
		if (Files.exists(path))
		{
			Files.delete(path);
			this.log.info("Deleted " + fileName);
		}
	}
}
