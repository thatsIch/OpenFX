package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessing.api.control.entity.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.PreProcessingConfig;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingFileStorageService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author thatsIch
 */
public class TrainPreProcessorCommand extends ACommand<IPreProcessing>
{
	// Properties
	private final IPreProcessor preProcessor;
	private final IFeature feature;
	private final PreProcessingFileStorageService storage;

	@Inject
	public TrainPreProcessorCommand(@Assisted IPreProcessor preProcessor, @Assisted IFeature feature, PreProcessingFileStorageService storage)
	{
		this.preProcessor = preProcessor;
		this.feature = feature;
		this.storage = storage;
	}

	@Override
	protected IPreProcessing call() throws Exception
	{
		final String preProcessorName = this.preProcessor.getName();
		final int featureVectorSize = this.feature.vectors().get(0).vector().size();
		final String id = UUID.randomUUID().toString();
		this.log.info(String.format("Prepared all data for Training [%s, %d, %s].", preProcessorName, featureVectorSize, id));

		final double[][] data = this.convertToNativeMatrix(this.feature);
		this.log.info("Prepared DataSets.");

		final PreProcessingConfig config = new PreProcessingConfig(preProcessorName, featureVectorSize, 0, id);
		this.log.info("Created PreProcessorConfiguration.");

		final IPreProcessing preProcessing = this.preProcessor.train(data, data, config);
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
			final List<Float> vector = featureVector.vector();
			final double[] featureArray = new double[vector.size()];
			int index = 0;
			for (float f : vector)
			{
				featureArray[index] = f;
				index++;
			}
			output.add(featureArray);
		});

		return output.toArray(new double[0][0]);
	}
}
