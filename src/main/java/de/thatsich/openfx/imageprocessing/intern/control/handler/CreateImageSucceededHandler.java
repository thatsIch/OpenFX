package de.thatsich.openfx.imageprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;

/**
 * Handler for what should happen if the Command was successfull
 * for adding the image to the input directory.
 *
 * @author Minh
 */
public class CreateImageSucceededHandler extends ACommandHandler<IImage>
{
	@Inject private IImages images;

	@Override
	public void handle(IImage image)
	{
		this.images.list().add(image);
		this.log.info("Added Image to Database");

		this.images.selected().set(image);
		this.log.info("Set currently selected Image to " + image);
	}
}
