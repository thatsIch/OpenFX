package de.thatsich.openfx.prediction.intern.control.entity;

import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;


public class NetworkPrediction implements INetworkPrediction
{
	// Properties
	private final ReadOnlyObjectWrapper<Mat> modified = new ReadOnlyObjectWrapper<>();
	//	private final ReadOnlyObjectWrapper<Mat> errorIndication = new ReadOnlyObjectWrapper<>();
	//	private final ReadOnlyObjectWrapper<Mat> errorPrediction = new ReadOnlyObjectWrapper<>();
	//	private final ReadOnlyStringWrapper classifierName = new ReadOnlyStringWrapper();
	//	private final ReadOnlyStringWrapper extractorName = new ReadOnlyStringWrapper();
	//	private final ReadOnlyIntegerWrapper frameSize = new ReadOnlyIntegerWrapper();
	//	private final ReadOnlyStringWrapper errorClassName = new ReadOnlyStringWrapper();
	//	private final ReadOnlyStringWrapper id = new ReadOnlyStringWrapper();

	private final ReadOnlyIntegerWrapper truePositive = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper falsePositive = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper trueNegative = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper falseNegative = new ReadOnlyIntegerWrapper();
	private final NetworkPredictionConfig config;

	public NetworkPrediction(NetworkPredictionConfig config, Mat modified)
	{
		this.config = config;
		this.modified.set(modified);

		//		this.calcTrueFalsePositiveNegative(errorIndication, errorPrediction);
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
		return this.modified.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty dateTime()
	{
		return this.config.dateTime;
	}

	@Override
	public ReadOnlyStringProperty predictedClassName()
	{
		return this.config.predictedClassName;
	}

	@Override
	public ReadOnlyStringProperty id()
	{
		return this.config.id;
	}

	@Override
	public NetworkPredictionConfig getConfig()
	{
		return this.config;
	}
	//
	//	@Override
	//	public ReadOnlyIntegerProperty truePositive()
	//	{
	//		return this.truePositive.getReadOnlyProperty();
	//	}
	//
	//	@Override
	//	public ReadOnlyIntegerProperty trueNegative()
	//	{
	//		return this.trueNegative.getReadOnlyProperty();
	//	}
	//
	//	@Override
	//	public ReadOnlyIntegerProperty falsePositive()
	//	{
	//		return this.falsePositive.getReadOnlyProperty();
	//	}
	//
	//	@Override
	//	public ReadOnlyIntegerProperty falseNegative()
	//	{
	//		return this.falseNegative.getReadOnlyProperty();
	//	}
}
