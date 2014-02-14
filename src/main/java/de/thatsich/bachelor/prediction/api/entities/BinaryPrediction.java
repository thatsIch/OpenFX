package de.thatsich.bachelor.prediction.api.entities;

import java.nio.file.Path;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import org.opencv.core.Mat;


public class BinaryPrediction
{

	// Properties
	private final ReadOnlyObjectWrapper<Path>	filePath		= new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat>	withError		= new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat>	errorIndication	= new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat>	errorPrediction	= new ReadOnlyObjectWrapper<>();
	private final ReadOnlyStringWrapper			classifierName	= new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper			extractorName	= new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper		frameSize		= new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper			errorClassName	= new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper			id				= new ReadOnlyStringWrapper();

	private final ReadOnlyIntegerWrapper		truePositive	= new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper		falsePositive	= new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper		trueNegative	= new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper		falseNegative	= new ReadOnlyIntegerWrapper();

	public BinaryPrediction( Path filePath, Mat withError, Mat errorIndication, Mat errorPrediction, String classifierName, String extractorName, int frameSize, String errorClassName, String id )
	{
		this.filePath.set( filePath );
		this.withError.set( withError );
		this.errorIndication.set( errorIndication );
		this.errorPrediction.set( errorPrediction );
		this.classifierName.set( classifierName );
		this.extractorName.set( extractorName );
		this.frameSize.set( frameSize );
		this.errorClassName.set( errorClassName );
		this.id.set( id );

		this.calcTrueFalsePositiveNegative( errorIndication, errorPrediction );
	}

	// Property Getters
	public ReadOnlyObjectProperty<Path> getFilePathProperty()
	{
		return this.filePath.getReadOnlyProperty();
	}
	public ReadOnlyObjectProperty<Mat> getWithErrorProperty()
	{
		return this.withError.getReadOnlyProperty();
	}
	public ReadOnlyObjectProperty<Mat> getErrorIndicationProperty()
	{
		return this.errorIndication.getReadOnlyProperty();
	}
	public ReadOnlyObjectProperty<Mat> getErrorPredictionProperty()
	{
		return this.errorPrediction.getReadOnlyProperty();
	}
	public ReadOnlyStringProperty getClassifierNameProperty()
	{
		return this.classifierName.getReadOnlyProperty();
	}
	public ReadOnlyStringProperty getExtractorNameProperty()
	{
		return this.extractorName.getReadOnlyProperty();
	}
	public ReadOnlyIntegerProperty getFrameSizeProperty()
	{
		return this.frameSize.getReadOnlyProperty();
	}
	public ReadOnlyStringProperty getErrorClassNameProperty()
	{
		return this.errorClassName.getReadOnlyProperty();
	}
	public ReadOnlyStringProperty getIDProperty()
	{
		return this.id.getReadOnlyProperty();
	}
	public ReadOnlyIntegerProperty getTruePositiveProperty()
	{
		return this.truePositive.getReadOnlyProperty();
	}
	public ReadOnlyIntegerProperty getTrueNegativeProperty()
	{
		return this.trueNegative.getReadOnlyProperty();
	}
	public ReadOnlyIntegerProperty getFalsePositiveProperty()
	{
		return this.falsePositive.getReadOnlyProperty();
	}
	public ReadOnlyIntegerProperty getFalseNegativeProperty()
	{
		return this.falseNegative.getReadOnlyProperty();
	}

	private void calcTrueFalsePositiveNegative( Mat onlyError, Mat prediction )
	{
		int truePositive = 0;
		int falsePositive = 0;
		int trueNegative = 0;
		int falseNegative = 0;

		for ( int row = 0; row < onlyError.rows(); row++ )
		{
			for ( int col = 0; col < onlyError.cols(); col++ )
			{
				final int errorValue = ( int ) onlyError.get( row, col )[0];
				final int predictionValue = ( int ) prediction.get( row, col )[0];

				if ( errorValue > 0 )
				{
					if ( predictionValue > 0.75 * 255 )
					{
						truePositive++;
					}
					else
					{
						trueNegative++;
					}
				}
				else
				{
					if ( predictionValue > 0.75 * 255 )
					{
						falsePositive++;
					}
					else
					{
						falseNegative++;
					}
				}
			}
		}

		this.truePositive.set( truePositive );
		this.trueNegative.set( trueNegative );
		this.falsePositive.set( falsePositive );
		this.falseNegative.set( falseNegative );
	}
}
