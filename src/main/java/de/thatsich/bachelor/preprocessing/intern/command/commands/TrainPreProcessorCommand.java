package de.thatsich.bachelor.preprocessing.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.PreProcessorConfiguration;
import de.thatsich.core.javafx.ACommand;
import org.opencv.core.MatOfFloat;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * TODO training of the preprocessor
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

		final MatOfFloat positive = new MatOfFloat();
		final MatOfFloat negative = new MatOfFloat();
		log.info("Prepared all data for Training.");

		// run through all FeatureVectorSets matching same categories
		// (same FrameSize, same Extractor, same ErrorClass)
		// which is not the selected one and their data to train
		// extract all float lists and transform them into MatOfFloats
		// use .t() on them to transpose them
		for (FeatureVectorSet set : this.featureVectorList)
		{
			// select only with same FeatureExtractor and FrameSize
			if (set.getExtractorNameProperty().get().equals(featureExtractorName) && set.getFrameSizeProperty().get() == frameSize)
			{
				for (FeatureVector vector : set.getFeatureVectorList())
				{
					final float[] floatArray = new float[vector.getVectorProperty().size()];
					int index = 0;
					for (float f : vector.getVectorProperty())
					{
						floatArray[index] = f;
						index++;
					}

					if (vector.getIsPositiveProperty().get())
					{
						positive.push_back(new MatOfFloat(floatArray).t());
					}
					else
					{
						negative.push_back(new MatOfFloat(floatArray).t());
					}
				}
			}
		}
		log.info("Prepared Negative and Positive DataSets.");

		final String filename = preProcessorName + "_" + featureExtractorName + "_" + frameSize + "_" + errorClassName + "_" + id + ".yaml";
		final Path filePath = this.preProcessingFolderPath.resolve(filename);
		log.info("Created FilePath");

		// TODO fix size
		final PreProcessorConfiguration config = new PreProcessorConfiguration(filePath, preProcessorName, 0, 0, id);
		log.info("Created BinaryClassifierConfiguration.");

		// TODO fix data in train method
		final double[][] data = new double[0][0];
		final IPreProcessing preProcessing = this.preProcessor.train(data, data, config);
		log.info("Trained Binary Classifier with " + positive + " positive and " + negative + " negative samples.");

		preProcessing.save(filePath.toString());
		log.info("Saved File to FileSystem.");

		return preProcessing;
	}

}
