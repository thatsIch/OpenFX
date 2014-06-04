package de.thatsich.openfx.errorgeneration.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import de.thatsich.openfx.errorgeneration.intern.control.command.ErrorInitCommander;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class ErrorDisplayPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ErrorInitCommander init;
	@Inject private IErrors errors;

	// Nodes
	@FXML private ImageView nodeImageViewError;

	@Override
	protected void bindComponents()
	{
		this.errors.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				final Image image = this.errorEntryToImage(newValue);
				this.nodeImageViewError.imageProperty().setValue(image);
				this.log.info("Selected new ErrorEntry.");
			}
			else
			{
				this.nodeImageViewError.imageProperty().set(null);
				this.log.info("Set Image to null.");
			}
		});
		this.log.info("Bound nodeImageViewError to Model.");
	}

	@Override
	protected void initComponents()
	{
		final IError entry = this.errors.selected().get();
		if (entry != null)
		{
			final Image image = this.errorEntryToImage(entry);
			this.nodeImageViewError.imageProperty().setValue(image);
			this.log.info("Initialized nodeImageViewError.");
		}
	}

	private Image errorEntryToImage(IError entry)
	{
		final Mat originalMat = entry.getOriginal().clone();
		final Mat onlyErrorMat = entry.getError();

		if (!originalMat.size().equals(onlyErrorMat.size()))
		{
			throw new IllegalStateException("Original Size: " + originalMat.size() + ", ErrorSize: " + onlyErrorMat.size());
		}

		// convert originalMat into RGB
		Imgproc.cvtColor(originalMat, originalMat, Imgproc.COLOR_GRAY2RGB);

		// overwrite error pixel in red layer
		for (int row = 0; row < originalMat.rows(); row++)
		{
			for (int col = 0; col < originalMat.cols(); col++)
			{
				final int value = (int) onlyErrorMat.get(row, col)[0];

				// if is error pixel overwrite tuple in original mat
				if (value > 0)
				{
					final double[] buffer = {0, 0, 255};
					originalMat.put(row, col, buffer);
				}
			}
		}

		return Images.toImage(originalMat);
	}
}
