package de.thatsich.openfx.prediction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.INetworkPredictions;
import de.thatsich.openfx.prediction.intern.control.command.PredictionInitCommander;
import de.thatsich.openfx.prediction.intern.control.evaluation.PrecisionRecall;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class PredictionDisplayPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PredictionInitCommander initCommander;
	@Inject private INetworkPredictions binaryPredictions;
	@Inject private PrecisionRecall precisionRecall;

	// Nodes
	@FXML private ImageView nodeImageViewPrediction;
	@FXML private Label nodeLabelPrecision;
	@FXML private Label nodeLabelRecall;
	@FXML private Label nodeLabelSpecificity;
	@FXML private Label nodeLabelAccuracy;
	@FXML private Label nodeLabelF05;
	@FXML private Label nodeLabelF1;
	@FXML private Label nodeLabelF2;

	@Override
	protected void bindComponents()
	{
	}

	@Override
	protected void initComponents()
	{
		this.initImageView();
	}

	private void initImageView()
	{
		this.initImageViewContent();
	}

	private void initImageViewContent()
	{
		this.binaryPredictions.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				final Image image = this.predictionToImage(newValue);
				this.nodeImageViewPrediction.imageProperty().set(image);

				final int truePositive = newValue.truePositive().get();
				final int falsePositive = newValue.falsePositive().get();
				final int trueNegative = newValue.trueNegative().get();
				final int falseNegative = newValue.falseNegative().get();

				final double precision = this.precisionRecall.precision(truePositive, falsePositive, trueNegative, falseNegative);
				final double recall = this.precisionRecall.recall(truePositive, falsePositive, trueNegative, falseNegative);

				this.nodeLabelPrecision.setText(precision + "");
				this.nodeLabelRecall.setText(recall + "");
				this.nodeLabelSpecificity.setText(this.precisionRecall.specificity(truePositive, falsePositive, trueNegative, falseNegative) + "");
				this.nodeLabelAccuracy.setText(this.precisionRecall.accuracy(truePositive, falsePositive, trueNegative, falseNegative) + "");

				this.nodeLabelF05.setText(this.precisionRecall.f05(precision, recall) + "");
				this.nodeLabelF1.setText(this.precisionRecall.f1(precision, recall) + "");
				this.nodeLabelF2.setText(this.precisionRecall.f2(precision, recall) + "");

				this.log.info("Selected new " + newValue);
			}
			else
			{
				this.nodeImageViewPrediction.imageProperty().set(null);

				this.nodeLabelPrecision.setText(null);
				this.nodeLabelRecall.setText(null);
				this.nodeLabelSpecificity.setText(null);
				this.nodeLabelAccuracy.setText(null);

				this.nodeLabelF05.setText(null);
				this.nodeLabelF1.setText(null);
				this.nodeLabelF2.setText(null);

				this.log.info("Deselected.");
			}
		});
	}

	private Image predictionToImage(INetworkPrediction prediction)
	{
		final Mat originalMat = prediction.modified().get().clone();
		final IError[][] errors = prediction.errors().get();
		final Double[][] predictions = prediction.errorPredictions().get();

		Imgproc.cvtColor(originalMat, originalMat, Imgproc.COLOR_GRAY2RGB);

		//		overwrite error pixel depending how they match with the prediction
		for (int row = 0; row < originalMat.rows(); row++)
		{
			for (int col = 0; col < originalMat.cols(); col++)
			{
				final int colIndex = col / 15;
				final int rowIndex = row / 15;
				if (colIndex < errors.length && rowIndex < errors[0].length)
				{
					final Mat errorMat = errors[colIndex][rowIndex].errorProperty().get();
					final double p = predictions[colIndex][rowIndex];
					final boolean hasError = this.hasError(errorMat);
					final double[] buffer = originalMat.get(row, col);

					// if error is there and predicted > Green
					if (hasError && p > 0.75)
					{
						buffer[1] = 255;
					}

					// if error is there but not predicted > Red
					else if (hasError && p <= 0.75)
					{
						buffer[2] = 255;
					}

					// if error is not there but predicted > Blue
					else if (!hasError && p > 0.75)
					{
						buffer[0] = 255;
					}

					// if error is not there and not predicted > Green
					//					else if (!hasError && p <= 0.75)
					//					{
					//						buffer[1] = 255;
					//					}

					originalMat.put(row, col, buffer);
				}
			}
		}

		return Images.toImage(originalMat);
	}

	private boolean hasError(Mat error)
	{
		return Core.sumElems(error).val[0] != 0;
	}
}
