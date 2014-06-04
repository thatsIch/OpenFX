package de.thatsich.openfx.imageprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.imageprocessing.api.model.IImageEntries;
import de.thatsich.openfx.imageprocessing.api.control.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for selecting the last selected image entry
 *
 * @author Minh
 */
public class GetLastImageEntryIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IImageEntries imageEntries;

	@Override
	public void handle(Integer value)
	{
		final ImageEntry selectedImageEntry = this.imageEntries.imageEntryListProperty().get(value);
		this.imageEntries.setSelectedImageEntry(selectedImageEntry);
		log.info("Set last selected image entry index in Model.");
	}
}