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

import java.util.IntSummaryStatistics;


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

	@FXML private Label nodeLabelPrecisionOverall;
	@FXML private Label nodeLabelRecallOverall;
	@FXML private Label nodeLabelSpecificityOverall;
	@FXML private Label nodeLabelAccuracyOverall;
	@FXML private Label nodeLabelF05Overall;
	@FXML private Label nodeLabelF1Overall;
	@FXML private Label nodeLabelF2Overall;

	@FXML private Label nodeLabelPrecisionStdDev;
	@FXML private Label nodeLabelRecallStdDev;
	@FXML private Label nodeLabelSpecificityStdDev;
	@FXML private Label nodeLabelAccuracyStdDev;
	@FXML private Label nodeLabelF05StdDev;
	@FXML private Label nodeLabelF1StdDev;
	@FXML private Label nodeLabelF2StdDev;

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

				final IntSummaryStatistics truePositiveStatistics = this.binaryPredictions.list().stream().mapToInt((x) -> x.truePositive().get()).summaryStatistics();
				final IntSummaryStatistics falsePositiveStatistics = this.binaryPredictions.list().stream().mapToInt((x) -> x.falsePositive().get()).summaryStatistics();
				final IntSummaryStatistics trueNegativeStatistics = this.binaryPredictions.list().stream().mapToInt((x) -> x.trueNegative().get()).summaryStatistics();
				final IntSummaryStatistics falseNegativeStatistics = this.binaryPredictions.list().stream().mapToInt((x) -> x.falseNegative().get()).summaryStatistics();

				final long truePositives = truePositiveStatistics.getSum();
				final long falsePositives = falsePositiveStatistics.getSum();
				final long trueNegatives = trueNegativeStatistics.getSum();
				final long falseNegatives = falseNegativeStatistics.getSum();

				final double precisionOverall = this.precisionRecall.precision(truePositives, falsePositives, trueNegatives, falseNegatives);
				final double recallOverall = this.precisionRecall.recall(truePositives, falsePositives, trueNegatives, falseNegatives);
				final double specificyOverrall = this.precisionRecall.specificity(truePositives, falsePositives, trueNegatives, falseNegatives);
				final double accuracyOverrall = this.precisionRecall.accuracy(truePositives, falsePositives, trueNegatives, falseNegatives);

				this.nodeLabelPrecisionOverall.setText(precisionOverall + "");
				this.nodeLabelRecallOverall.setText(recallOverall + "");
				this.nodeLabelSpecificityOverall.setText(specificyOverrall + "");
				this.nodeLabelAccuracyOverall.setText(accuracyOverrall + "");

				this.nodeLabelF05Overall.setText(this.precisionRecall.f05(precisionOverall, recallOverall) + "");
				this.nodeLabelF1Overall.setText(this.precisionRecall.f1(precisionOverall, recallOverall) + "");
				this.nodeLabelF2Overall.setText(this.precisionRecall.f2(precisionOverall, recallOverall) + "");

				double precisionSum = 0;
				double recallSum = 0;
				double specifitySum = 0;
				double accuracySum = 0;
				double f05Sum = 0;
				double f1Sum = 0;
				double f2Sum = 0;

				for (INetworkPrediction prediction : this.binaryPredictions.list())
				{
					final int tP = prediction.truePositive().get();
					final int fP = prediction.falsePositive().get();
					final int tN = prediction.trueNegative().get();
					final int fN = prediction.falseNegative().get();

					final double p = this.precisionRecall.precision(tP, fP, tN, fN);
					final double r = this.precisionRecall.recall(tP, fP, tN, fN);

					precisionSum += p;
					recallSum += r;
					specifitySum += this.precisionRecall.specificity(tP, fP, tN, fN);
					accuracySum += this.precisionRecall.accuracy(tP, fP, tN, fN);
					f05Sum += this.precisionRecall.f05(p, r);
					f1Sum += this.precisionRecall.f1(p, r);
					f2Sum += this.precisionRecall.f2(p, r);
				}

				final int length = this.binaryPredictions.list().size();
				final double precisionAverage = precisionSum / length;
				final double recallAverage = recallSum / length;
				final double specifityAverage = specifitySum / length;
				final double accuracyAverage = accuracySum / length;
				final double f05Average = f05Sum / length;
				final double f1Average = f1Sum / length;
				final double f2Average = f2Sum / length;

				double precisionSquaredSum = 0;
				double recallSquaredSum = 0;
				double specifitySquaredSum = 0;
				double accuracySquaredSum = 0;
				double f05SquaredSum = 0;
				double f1SquaredSum = 0;
				double f2SquaredSum = 0;

				for (INetworkPrediction prediction : this.binaryPredictions.list())
				{
					final int tP = prediction.truePositive().get();
					final int fP = prediction.falsePositive().get();
					final int tN = prediction.trueNegative().get();
					final int fN = prediction.falseNegative().get();

					final double p = this.precisionRecall.precision(tP, fP, tN, fN);
					final double r = this.precisionRecall.recall(tP, fP, tN, fN);
					final double s = this.precisionRecall.specificity(tP, fP, tN, fN);
					final double a = this.precisionRecall.accuracy(tP, fP, tN, fN);

					precisionSquaredSum += Math.pow(p - precisionAverage, 2);
					recallSquaredSum += Math.pow(r - recallAverage, 2);
					specifitySquaredSum += Math.pow(s - specifityAverage, 2);
					accuracySquaredSum += Math.pow(a - accuracyAverage, 2);

					f05SquaredSum += Math.pow(this.precisionRecall.f05(p, r) - f05Average, 2);
					f1SquaredSum += Math.pow(this.precisionRecall.f1(p, r) - f1Average, 2);
					f2SquaredSum += Math.pow(this.precisionRecall.f2(p, r) - f2Average, 2);
				}

				final double precisionNormed = precisionSquaredSum / length;
				final double recallNormed = recallSquaredSum / length;
				final double specificityNormed = specifitySquaredSum / length;
				final double accuracyNormed = accuracySquaredSum / length;
				final double f05Normed = f05SquaredSum / length;
				final double f1Normed = f1SquaredSum / length;
				final double f2Normed = f2SquaredSum / length;

				this.nodeLabelPrecisionStdDev.setText(precisionNormed + "");
				this.nodeLabelRecallStdDev.setText(recallNormed + "");
				this.nodeLabelSpecificityStdDev.setText(specificityNormed + "");
				this.nodeLabelAccuracyStdDev.setText(accuracyNormed + "");

				this.nodeLabelF05StdDev.setText(f05Normed + "");
				this.nodeLabelF1StdDev.setText(f1Normed + "");
				this.nodeLabelF2StdDev.setText(f2Normed + "");

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

				this.nodeLabelPrecisionOverall.setText(null);
				this.nodeLabelRecallOverall.setText(null);
				this.nodeLabelSpecificityOverall.setText(null);
				this.nodeLabelAccuracyOverall.setText(null);

				this.nodeLabelF05Overall.setText(null);
				this.nodeLabelF1Overall.setText(null);
				this.nodeLabelF2Overall.setText(null);

				this.nodeLabelPrecisionStdDev.setText(null);
				this.nodeLabelRecallStdDev.setText(null);
				this.nodeLabelSpecificityStdDev.setText(null);
				this.nodeLabelAccuracyStdDev.setText(null);

				this.nodeLabelF05StdDev.setText(null);
				this.nodeLabelF1StdDev.setText(null);
				this.nodeLabelF2StdDev.setText(null);

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
