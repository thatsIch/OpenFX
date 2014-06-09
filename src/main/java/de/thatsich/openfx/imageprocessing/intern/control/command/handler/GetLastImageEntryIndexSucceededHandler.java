package de.thatsich.openfx.imageprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for selecting the last selected image entry
 *
 * @author Minh
 */
public class GetLastImageEntryIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IImages images;

	@Override
	public void handle(Integer value)
	{
		final List<IImage> list = this.images.list();

		if (value >= 0 && list.size() > 0)
		{
			final IImage selected = list.get(value);
			this.images.selected().set(selected);
			this.log.info("Set last selected image index in Model.");
		}
	}
}
