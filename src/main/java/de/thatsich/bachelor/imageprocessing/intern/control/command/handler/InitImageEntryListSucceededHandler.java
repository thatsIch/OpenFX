package de.thatsich.bachelor.imageprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.imageprocessing.api.model.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.control.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;

/**
 * Handler for what should happen if the Command was successfull
 * for initializing all images entries
 *
 * @author Minh
 */
public class InitImageEntryListSucceededHandler extends ACommandHandler<List<ImageEntry>>
{

	@Inject
	private IImageEntries imageEntries;

	@Override
	public void handle(List<ImageEntry> value)
	{
		imageEntries.imageEntryListProperty().get().addAll(value);
		log.info("Added ImageEntryList to Model.");
	}
}
