package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageFileStorageService;

public class DeleteImageCommand extends ACommand<IImage>
{
	final private IImage image;
	private final ImageFileStorageService storage;

	@Inject
	public DeleteImageCommand(@Assisted IImage entry, ImageFileStorageService storage)
	{
		this.image = entry;
		this.storage = storage;
	}

	@Override
	protected IImage call() throws Exception
	{
		this.storage.delete(this.image);

		return this.image;
	}
}
