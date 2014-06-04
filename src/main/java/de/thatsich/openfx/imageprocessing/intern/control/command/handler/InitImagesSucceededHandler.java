package de.thatsich.openfx.imageprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing all images entries
 *
 * @author Minh
 */
public class InitImagesSucceededHandler extends ACommandHandler<List<IImage>>
{

	@Inject
	private IImages images;

	@Override
	public void handle(List<IImage> value)
	{
		this.images.list().get().addAll(value);
		this.log.info("Added Images to Model.");
	}
}
