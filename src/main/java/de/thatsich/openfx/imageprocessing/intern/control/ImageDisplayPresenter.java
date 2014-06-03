package de.thatsich.openfx.imageprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.openfx.imageprocessing.api.model.IImageEntries;
import de.thatsich.openfx.imageprocessing.api.control.ImageEntry;
import de.thatsich.openfx.imageprocessing.intern.control.command.ImageInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageDisplayPresenter extends AFXMLPresenter
{

	@Inject ImageInitCommander initCommander;
	// Nodes
	@FXML ImageView nodeImageViewInput;
	// Injects
	@Inject IImageEntries imageEntries;

	@Override
	protected void bindComponents()
	{
		this.imageEntries.selectedImageEntryProperty().addListener(new ChangeListener<ImageEntry>()
		{
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue)
			{
				nodeImageViewInput.imageProperty().setValue((newValue != null) ? Images.toImage(newValue.getImageMat()) : null);
			}
		});
		this.log.info("Bound ImageView to Model.");
	}

	@Override
	protected void initComponents()
	{
		ImageEntry entry = this.imageEntries.selectedImageEntryProperty().get();
		if (entry != null)
		{
			final Image entryImage = Images.toImage(entry.getImageMat());
			this.nodeImageViewInput.imageProperty().setValue(entryImage);
			this.log.info("Initialized nodeImageViewInput.");
		}
	}
}
