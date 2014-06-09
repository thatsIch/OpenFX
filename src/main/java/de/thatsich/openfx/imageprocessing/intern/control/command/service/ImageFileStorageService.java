package de.thatsich.openfx.imageprocessing.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.openfx.imageprocessing.intern.control.entity.Image;
import de.thatsich.openfx.imageprocessing.intern.control.entity.ImageConfig;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ImageFileStorageService extends AFileStorageService<IImage>
{
	@Inject
	protected ImageFileStorageService(IImageState state)
	{
		super(state.path().get());
	}

	@Override
	public List<IImage> init() throws IOException
	{
		final List<IImage> result = new LinkedList<>();
		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";

		if (Files.notExists(this.storagePath) || !Files.isDirectory(this.storagePath))
		{
			Files.createDirectories(this.storagePath);
		}

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath, GLOB_PATTERN))
		{
			for (Path child : stream)
			{
				final IImage load = this.retrieve(child);

				result.add(load);
				this.log.info("Added " + child);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + result.size() + ".");

		return result;
	}

	@Override
	public IImage create(final IImage elem) throws IOException
	{
		final Path filePath = this.storagePath.resolve(elem.getImageName());
		if (!Files.exists(filePath))
		{
			Images.store(elem.getImageMat(), filePath);
			return elem;
		}

		return null;
	}

	@Override
	public IImage retrieve(final Path path) throws IOException
	{
		final Path imagePath = path.toAbsolutePath();
		final Mat imageMat = Highgui.imread(imagePath.toString(), 0);
		final String fileName = path.getFileName().toString();
		final ImageConfig config = new ImageConfig(fileName);

		return new Image(config, imageMat);
	}

	@Override
	public IImage update(final IImage elem) throws IOException
	{
		final Path filePath = this.storagePath.resolve(elem.getImageName());
		Images.store(elem.getImageMat(), filePath);

		return elem;
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
