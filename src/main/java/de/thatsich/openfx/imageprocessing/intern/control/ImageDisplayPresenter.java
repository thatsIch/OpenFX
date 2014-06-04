package de.thatsich.openfx.imageprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import de.thatsich.openfx.imageprocessing.intern.control.command.ImageInitCommander;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ImageDisplayPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ImageInitCommander init;
	@Inject private IImages images;

	// Nodes
	@FXML private ImageView nodeImageViewInput;

	@Override
	protected void bindComponents()
	{
		this.images.selected().addListener((observable, oldValue, newValue) -> this.nodeImageViewInput.imageProperty().setValue((newValue != null) ? Images.toImage(newValue.getImageMat()) : null));
		this.log.info("Bound ImageView to Model.");
	}

	@Override
	protected void initComponents()
	{
		final IImage entry = this.images.selected().get();
		if (entry != null)
		{
			final javafx.scene.image.Image entryImage = Images.toImage(entry.getImageMat());
			this.nodeImageViewInput.imageProperty().setValue(entryImage);
			this.log.info("Initialized nodeImageViewInput.");
		}
	}
}
