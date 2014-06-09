package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageFileStorageService;

import java.util.List;

public class InitImagesCommand extends ACommand<List<IImage>>
{
	private final ImageFileStorageService storage;

	@Inject
	protected InitImagesCommand(ImageFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<IImage> call() throws Exception
	{
		return this.storage.init();
	}

}
