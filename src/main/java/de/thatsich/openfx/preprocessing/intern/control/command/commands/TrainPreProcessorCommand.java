package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.PreProcessorConfiguration;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * TODO training of the preprocessor
 * TODO loading of preprocessors
 *
 * @author thatsIch
 */
public class TrainPreProcessorCommand extends ACommand<IPreProcessing>
{
	// Properties
	private final Path preProcessingFolderPath;
	private final IPreProcessor preProcessor;
	private final IFeature selectedFeatureVector;
	private final List<IFeature> featureVectorList;

	@Inject
	public TrainPreProcessorCommand(@Assisted Path preProcessingFolderPath, @Assisted IPreProcessor preProcessor, @Assisted IFeature selected, @Assisted List<IFeature> all)
	{
		this.preProcessingFolderPath = preProcessingFolderPath;
		this.preProcessor = preProcessor;
		this.selectedFeatureVector = selected;
		this.featureVectorList = all;
	}

	@Override
	protected IPreProcessing call() throws Exception
	{
		final String preProcessorName = this.preProcessor.getName();
		final String featureExtractorName = this.selectedFeatureVector.extractorName().get();
		final int frameSize = this.selectedFeatureVector.tileSize().get();
		final String errorClassName = this.selectedFeatureVector.className().get();
		final String id = UUID.randomUUID().toString();
		this.log.info("Prepared all data for Training.");

		final double[][] data = this.convertToNativeMatrix(this.featureVectorList, featureExtractorName, frameSize);
		final int featureVectorSize = this.featureVectorList.get(0).vectors().size();
		this.log.info("Prepared DataSets.");

		final String filename = preProcessorName + "_" + featureExtractorName + "_" + frameSize + "_" + errorClassName + "_" + id + ".yaml";
		final Path filePath = this.preProcessingFolderPath.resolve(filename);
		this.log.info("Created FilePath");

		final PreProcessorConfiguration config = new PreProcessorConfiguration(filePath, preProcessorName, featureVectorSize, 0, id);
		this.log.info("Created PreProcessorConfiguration.");

		final IPreProcessing preProcessing = this.preProcessor.train(data, data, config);
		this.log.info("Trained AANN with " + Arrays.deepToString(data) + " samples.");

		preProcessing.save(filePath.toString());
		this.log.info("Saved File to FileSystem.");

		return preProcessing;
	}

	// run through all FeatureVectorSets matching same categories
	// (same FrameSize, same Extractor, same ErrorClass)
	// which is not the selected one and their data to train
	// extract all float lists and transform them into MatOfFloats
	// use .t() on them to transpose them
	double[][] convertToNativeMatrix(final List<IFeature> input, String name, int frameSize)
	{
		final List<double[]> output = new LinkedList<>();

		for (IFeature set : input)
		{
			final boolean equalName = set.extractorName().get().equals(name);
			final boolean equalSize = set.tileSize().get() == frameSize;

			// select only with same FeatureExtractor and FrameSize
			if (equalName && equalSize)
			{
				for (IFeatureVector vector : set.vectors())
				{
					final double[] featureArray = new double[vector.vector().size()];
					int index = 0;
					for (float f : vector.vector())
					{
						featureArray[index] = f;
						index++;
					}
					output.add(featureArray);
				}
			}
		}

		return output.toArray(new double[0][0]);
	}
}
