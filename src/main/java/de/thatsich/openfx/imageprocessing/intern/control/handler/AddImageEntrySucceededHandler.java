package de.thatsich.openfx.imageprocessing.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.imageprocessing.api.model.IImageEntries;
import de.thatsich.openfx.imageprocessing.api.control.ImageEntry;
import de.thatsich.core.javafx.ACommandHandler;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import java.nio.file.Path;

/**
 * Handler for what should happen if the Command was successfull
 * for adding the image to the input directory.
 *
 * @author Minh
 */
public class AddImageEntrySucceededHandler extends ACommandHandler<Path>
{

	@Inject
	private IImageEntries imageEntries;

	@Override
	public void handle(Path value)
	{
		final Mat copiedMat = Highgui.imread(value.toString(), 0);
		final ImageEntry copy = new ImageEntry(value, copiedMat);
		this.imageEntries.imageEntryListProperty().get().add(copy);
		this.log.info("Added copy to ChoiceBoxDisplayImage: " + value.toString());

		this.imageEntries.selectedImageEntryProperty().set(copy);
		this.log.info("Set currently selected Image to " + value);
	}
}
