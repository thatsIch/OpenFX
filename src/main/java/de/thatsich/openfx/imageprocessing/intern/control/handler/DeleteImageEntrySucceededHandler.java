package de.thatsich.openfx.imageprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for deleting the image out of the input directory.
 *
 * @author Minh
 */
public class DeleteImageEntrySucceededHandler extends ACommandHandler<IImage>
{
	@Inject private IImages images;

	@Override
	public void handle(IImage deletion)
	{
		final List<IImage> imageEntryList = this.images.list().get();
		imageEntryList.remove(deletion);
		this.log.info("Removed ImageEntry from Database.");

		if (imageEntryList.size() > 0)
		{
			final IImage first = imageEntryList.get(0);
			this.images.selected().set(first);
			this.log.info("Reset Selection to the first.");
		}
		else
		{
			this.images.selected().set(null);
		}
	}
}
