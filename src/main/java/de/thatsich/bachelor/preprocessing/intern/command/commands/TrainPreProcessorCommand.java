package de.thatsich.bachelor.preprocessing.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.core.javafx.ACommand;

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
	private final FeatureVectorSet selectedFeatureVector;
	private final List<FeatureVectorSet> featureVectorList;

	@Inject
	public TrainPreProcessorCommand(@Assisted Path preProcessingFolderPath, @Assisted IPreProcessor preProcessor, @Assisted FeatureVectorSet selected, @Assisted List<FeatureVectorSet> all)
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
		final String featureExtractorName = this.selectedFeatureVector.getExtractorNameProperty().get();
		final int frameSize = this.selectedFeatureVector.getFrameSizeProperty().get();
		final String errorClassName = this.selectedFeatureVector.getClassNameProperty().get();
		final String id = UUID.randomUUID().toString();
		log.info("Prepared all data for Training.");

		final double[][] data = this.convertToNativeMatrix(this.featureVectorList, featureExtractorName, frameSize);
		final int featureVectorSize = this.featureVectorList.get(0).getFeatureVectorList().size();
		log.info("Prepared DataSets.");

		final String filename = preProcessorName + "_" + featureExtractorName + "_" + frameSize + "_" + errorClassName + "_" + id + ".yaml";
		final Path filePath = this.preProcessingFolderPath.resolve(filename);
		log.info("Created FilePath");

		final PreProcessorConfiguration config = new PreProcessorConfiguration(filePath, preProcessorName, featureVectorSize, 0, id);
		log.info("Created PreProcessorConfiguration.");

		final IPreProcessing preProcessing = this.preProcessor.train(data, data, config);
		log.info("Trained AANN with " + Arrays.deepToString(data) + " samples.");

		preProcessing.save(filePath.toString());
		log.info("Saved File to FileSystem.");

		return preProcessing;
	}

	// run through all FeatureVectorSets matching same categories
	// (same FrameSize, same Extractor, same ErrorClass)
	// which is not the selected one and their data to train
	// extract all float lists and transform them into MatOfFloats
	// use .t() on them to transpose them
	double[][] convertToNativeMatrix(final List<FeatureVectorSet> input, String name, int frameSize)
	{
		final List<double[]> output = new LinkedList<>();

		for (FeatureVectorSet set : input)
		{
			final boolean equalName = set.getExtractorNameProperty().get().equals(name);
			final boolean equalSize =set.getFrameSizeProperty().get() == frameSize;

			// select only with same FeatureExtractor and FrameSize
			if (equalName && equalSize)
			{
				for (FeatureVector vector : set.getFeatureVectorList())
				{
					final double[] featureArray = new double[vector.getVectorProperty().size()];
					int index = 0;
					for (float f : vector.getVectorProperty())
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
