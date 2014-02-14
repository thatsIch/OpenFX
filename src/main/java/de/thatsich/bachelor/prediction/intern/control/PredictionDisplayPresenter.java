package de.thatsich.bachelor.prediction.intern.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.command.PredictionInitCommander;
import de.thatsich.bachelor.prediction.intern.command.evaluation.PrecisionRecall;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;


public class PredictionDisplayPresenter extends AFXMLPresenter
{
	// Nodes
	@FXML private ImageView				nodeImageViewPrediction;
	@FXML private Label					nodeLabelPrecision;
	@FXML private Label					nodeLabelRecall;
	@FXML private Label					nodeLabelSpecificity;
	@FXML private Label					nodeLabelAccuracy;

	// Injects
	@Inject private IBinaryPredictions	binaryPredictions;
	@Inject private PrecisionRecall		precisionRecall;

	@Inject PredictionInitCommander		initCommander;

	@Override
	protected void initComponents()
	{
		this.initImageView();
	}

	@Override
	protected void bindComponents()
	{
	}

	private void initImageView()
	{
		this.initImageViewContent();
	}

	private void initImageViewContent()
	{
		this.binaryPredictions.getSelectedBinaryPredictionProperty().addListener( new ChangeListener<BinaryPrediction>()
		{
			@Override
			public void changed( ObservableValue<? extends BinaryPrediction> observable, BinaryPrediction oldValue, BinaryPrediction newValue )
			{
				if ( newValue != null )
				{
					final Image image = predictionToImage( newValue );
					nodeImageViewPrediction.imageProperty().set( image );

					final int truePositive = newValue.getTruePositiveProperty().get();
					final int falsePositive = newValue.getFalsePositiveProperty().get();
					final int trueNegative = newValue.getTrueNegativeProperty().get();
					final int falseNegative = newValue.getFalseNegativeProperty().get();
					
					nodeLabelPrecision.setText( precisionRecall.precision( truePositive, falsePositive, trueNegative, falseNegative ) + "" );
					nodeLabelRecall.setText( precisionRecall.recall( truePositive, falsePositive, trueNegative, falseNegative ) + "" );
					nodeLabelSpecificity.setText( precisionRecall.specificity( truePositive, falsePositive, trueNegative, falseNegative ) + "" );
					nodeLabelAccuracy.setText( precisionRecall.accuracy( truePositive, falsePositive, trueNegative, falseNegative ) + "" );
					
					log.info( "Selected new " + newValue );
				}
				else
				{
					nodeImageViewPrediction.imageProperty().set( null );
					
					nodeLabelPrecision.setText( null );
					nodeLabelRecall.setText( null );
					nodeLabelSpecificity.setText( null );
					nodeLabelAccuracy.setText( null );
					
					log.info( "Deselected." );
				}
			}
		} );
	}

	private Image predictionToImage( BinaryPrediction prediction )
	{
		final Mat originalMat = prediction.getWithErrorProperty().get().clone();
		final Mat onlyErrorMat = prediction.getErrorIndicationProperty().get();
		final Mat onlyPrediction = prediction.getErrorPredictionProperty().get();

		// convert originalMat into RGB
		Imgproc.cvtColor( originalMat, originalMat, Imgproc.COLOR_GRAY2RGB );

		// overwrite error pixel depending how they match with the prediction
		for ( int row = 0; row < originalMat.rows(); row++ )
		{
			for ( int col = 0; col < originalMat.cols(); col++ )
			{
				final int errorValue = ( int ) onlyErrorMat.get( row, col )[0];
				final int predictionValue = ( int ) onlyPrediction.get( row, col )[0];
				final double[] buffer = originalMat.get( row, col );

				// if error is there and is found > Green
				if ( errorValue > 0 && predictionValue > 0.75 * 255 )
				{
					buffer[1] = 255;
				}

				// if error is there but not predicted > Red
				else if ( errorValue > 0 && predictionValue <= 0.75 * 255 )
				{
					buffer[2] = 255;
				}

				// if error is not there but predicted > Red
				else if ( errorValue == 0 && predictionValue > 0.75 * 255 )
				{
//					buffer[0] = 255;
				}

				// if error is not there and predicted > Green
				else if ( errorValue == 0 && predictionValue <= 7.25 * 255 )
				{
//					buffer[1] = 255;
				}

				originalMat.put( row, col, buffer );
			}
		}

		return Images.toImage( originalMat );
	}

	// TODO use sigmoid?
	public static double sigmoid( double x )
	{
		return 1 / ( 1 + Math.exp( -x ) );
	}
}
