package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.TrainedPreProcessorConfig;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author thatsIch
 */
public class CreateTrainedPreProcessorCommand extends ACommand<ITrainedPreProcessor>
{
	// Properties
	private final IPreProcessor preProcessor;
	private final IFeature feature;
	private final TrainedPreProcessorFileStorageService storage;

	@Inject
	public CreateTrainedPreProcessorCommand(@Assisted IPreProcessor preProcessor, @Assisted IFeature feature, TrainedPreProcessorFileStorageService storage)
	{
		this.preProcessor = preProcessor;
		this.feature = feature;
		this.storage = storage;
	}

	@Override
	public ITrainedPreProcessor call() throws Exception
	{
		final String preProcessorName = this.preProcessor.getName();
		final int featureVectorSize = this.feature.vectors().get(0).vector().size();
		final String id = UUID.randomUUID().toString();
		this.log.info(String.format("Prepared all data for Training [%s, %d, %s].", preProcessorName, featureVectorSize, id));

		final double[][] data = this.convertToNativeMatrix(this.feature);
		this.log.info("Prepared DataSets.");

		final TrainedPreProcessorConfig config = new TrainedPreProcessorConfig(preProcessorName, featureVectorSize, 0, id);
		this.log.info("Created PreProcessorConfiguration.");

		final ITrainedPreProcessor preProcessing = this.preProcessor.train(data, data, config);
		this.log.info("Trained with " + Arrays.deepToString(data) + " samples.");

		this.storage.create(preProcessing);
		this.log.info("Saved File to FileSystem.");

		return preProcessing;
	}

	/**
	 * Converts the feature into a double matrix
	 *
	 * @param feature to be converted feature, has a list of featurevectors in it
	 *
	 * @return double matrix containing the feature vectors [feature][featurevector]
	 */
	double[][] convertToNativeMatrix(final IFeature feature)
	{
		final List<double[]> output = new LinkedList<>();

		feature.vectors().forEach(featureVector -> {
			final List<Double> vector = featureVector.vector();
			final double[] featureArray = new double[vector.size()];
			int index = 0;
			for (double d : vector)
			{
				featureArray[index] = d;
				index++;
			}
			output.add(featureArray);
		});

		return output.toArray(new double[0][0]);
	}
}
