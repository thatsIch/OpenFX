package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingFileStorageService;

import java.nio.file.Path;
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
	private final Path path;
	private final IPreProcessor preProcessor;
	private final IFeature feature;
	private final PreProcessingFileStorageService storage;

	@Inject
	public TrainPreProcessorCommand(@Assisted Path path, @Assisted IPreProcessor preProcessor, @Assisted IFeature feature, PreProcessingFileStorageService storage)
	{
		this.path = path;
		this.preProcessor = preProcessor;
		this.feature = feature;
		this.storage = storage;
	}

	@Override
	protected IPreProcessing call() throws Exception
	{
		final String preProcessorName = this.preProcessor.getName();
		final String featureExtractorName = this.feature.extractorName().get();
		final int frameSize = this.feature.tileSize().get();
		final String errorClassName = this.feature.className().get();
		final String id = UUID.randomUUID().toString();
		this.log.info("Prepared all data for Training.");

		final double[][] data = this.convertToNativeMatrix(this.feature);
		final int featureVectorSize = this.feature.vectors().size();
		this.log.info("Prepared DataSets.");

		final String filename = preProcessorName + "_" + featureExtractorName + "_" + frameSize + "_" + errorClassName + "_" + id + ".yaml";
		final Path filePath = this.path.resolve(filename);
		this.log.info("Created FilePath");

		final PreProcessorConfiguration config = new PreProcessorConfiguration(filePath, preProcessorName, featureVectorSize, 0, id);
		this.log.info("Created PreProcessorConfiguration.");

		final IPreProcessing preProcessing = this.preProcessor.train(data, data, config);
		this.log.info("Trained AANN with " + Arrays.deepToString(data) + " samples.");

		this.storage.create(preProcessing);
		this.log.info("Saved File to FileSystem.");

		return preProcessing;
	}

	// run through all FeatureVectorSets matching same categories
	// (same FrameSize, same Extractor, same ErrorClass)
	// which is not the selected one and their data to train
	// extract all float lists and transform them into MatOfFloats
	// use .t() on them to transpose them
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
