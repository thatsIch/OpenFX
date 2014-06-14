package de.thatsich.openfx.prediction.intern.control.entity;

import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import org.opencv.core.Mat;


public class BinaryPrediction implements IBinaryPrediction
{
	// Properties
	private final ReadOnlyObjectWrapper<Mat> withError = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> errorIndication = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Mat> errorPrediction = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyStringWrapper classifierName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	private final ReadOnlyStringWrapper errorClassName = new ReadOnlyStringWrapper();
	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();

	private final ReadOnlyIntegerWrapper truePositive = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper falsePositive = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper trueNegative = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper falseNegative = new ReadOnlyIntegerWrapper();

	public BinaryPrediction(Mat withError, Mat errorIndication, Mat errorPrediction, String classifierName, String extractorName, int frameSize, String errorClassName, String id)
	{
		this.withError.set(withError);
		this.errorIndication.set(errorIndication);
		this.errorPrediction.set(errorPrediction);
		this.classifierName.set(classifierName);
		this.extractorName.set(extractorName);
		this.frameSize.set(frameSize);
		this.errorClassName.set(errorClassName);
		this.id.set(id);

		this.calcTrueFalsePositiveNegative(errorIndication, errorPrediction);
	}

	private void calcTrueFalsePositiveNegative(Mat onlyError, Mat prediction)
	{
		int truePositive = 0;
		int falsePositive = 0;
		int trueNegative = 0;
		int falseNegative = 0;

		for (int row = 0; row < onlyError.rows(); row++)
		{
			for (int col = 0; col < onlyError.cols(); col++)
			{
				final int errorValue = (int) onlyError.get(row, col)[0];
				final int predictionValue = (int) prediction.get(row, col)[0];

				if (errorValue > 0)
				{
					if (predictionValue > 0.75 * 255)
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
					if (predictionValue > 0.75 * 255)
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

		this.truePositive.set(truePositive);
		this.trueNegative.set(trueNegative);
		this.falsePositive.set(falsePositive);
		this.falseNegative.set(falseNegative);
	}

	// Property Getters
	@Override
	public ReadOnlyObjectProperty<Mat> modified()
	{
		return this.withError.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<Mat> errorIndication()
	{
		return this.errorIndication.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<Mat> errorPrediction()
	{
		return this.errorPrediction.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty classifierName()
	{
		return this.classifierName.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty extractorName()
	{
		return this.extractorName.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyIntegerProperty frameSize()
	{
		return this.frameSize.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty errorClassName()
	{
		return this.errorClassName.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty id()
	{
		return this.id.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyIntegerProperty truePositive()
	{
		return this.truePositive.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyIntegerProperty trueNegative()
	{
		return this.trueNegative.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyIntegerProperty falsePositive()
	{
		return this.falsePositive.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyIntegerProperty falseNegative()
	{
		return this.falseNegative.getReadOnlyProperty();
	}
}
