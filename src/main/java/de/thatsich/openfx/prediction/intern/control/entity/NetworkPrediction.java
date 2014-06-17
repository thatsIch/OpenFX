package de.thatsich.openfx.prediction.intern.control.entity;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Core;
import org.opencv.core.Mat;


public class NetworkPrediction implements INetworkPrediction
{
	// Properties
	private final ReadOnlyObjectWrapper<Mat> modified = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<IError[][]> errors = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<String[][]> errorClasses = new ReadOnlyObjectWrapper<>();
	private final ReadOnlyObjectWrapper<Double[][]> errorPredictionMat = new ReadOnlyObjectWrapper<>();

	private final ReadOnlyIntegerWrapper truePositive = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper falsePositive = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper trueNegative = new ReadOnlyIntegerWrapper();
	private final ReadOnlyIntegerWrapper falseNegative = new ReadOnlyIntegerWrapper();

	private final NetworkPredictionConfig config;

	public NetworkPrediction(NetworkPredictionConfig config, Mat modified, IError[][] errorSplit, String[][] errorClasses, Double[][] errorPredictionMat)
	{
		this.config = config;

		this.modified.set(modified);
		this.errors.set(errorSplit);
		this.errorClasses.set(errorClasses);
		this.errorPredictionMat.set(errorPredictionMat);

		System.out.println("Set splits");

		this.calcTrueFalsePositiveNegative(errorSplit, errorPredictionMat);
	}

	private void calcTrueFalsePositiveNegative(IError[][] onlyError, Double[][] prediction)
	{
		int truePositive = 0;
		int falsePositive = 0;
		int trueNegative = 0;
		int falseNegative = 0;

		final int cols = onlyError.length;
		final int rows = onlyError[0].length;

		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				final Mat error = onlyError[col][row].errorProperty().get();
				final double pred = prediction[col][row];
				if (this.hasError(error))
				{
					if (pred > 0.75)
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
					if (pred > 0.75)
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

	private boolean hasError(Mat error)
	{
		return Core.sumElems(error).val[0] != 0;
	}

	// Property Getters
	@Override
	public ReadOnlyObjectProperty<Mat> modified()
	{
		return this.modified.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<IError[][]> errors()
	{
		return this.errors.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<String[][]> errorClasses() { return this.errorClasses.getReadOnlyProperty();}

	@Override
	public ReadOnlyObjectProperty<Double[][]> errorPredictions() { return this.errorPredictionMat.getReadOnlyProperty();}

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
