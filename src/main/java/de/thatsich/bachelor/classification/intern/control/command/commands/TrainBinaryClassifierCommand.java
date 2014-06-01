package de.thatsich.bachelor.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.classification.api.control.IBinaryClassification;
import de.thatsich.bachelor.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.bachelor.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.core.javafx.ACommand;
import org.opencv.core.MatOfFloat;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


public class TrainBinaryClassifierCommand extends ACommand<IBinaryClassification>
{
	// Properties
	private final Path binaryClassifierFolderPath;
	private final IBinaryClassifier binaryClassifier;
	private final FeatureVectorSet selectedFeatureVector;
	private final List<FeatureVectorSet> featureVectorList;

	@Inject
	public TrainBinaryClassifierCommand(@Assisted Path binaryClassifierFolderPath, @Assisted IBinaryClassifier classifier, @Assisted FeatureVectorSet selected, @Assisted List<FeatureVectorSet> all)
	{
		this.binaryClassifierFolderPath = binaryClassifierFolderPath;
		this.binaryClassifier = classifier;
		this.selectedFeatureVector = selected;
		this.featureVectorList = all;
	}

	@Override
	protected IBinaryClassification call() throws Exception
	{
		final String binaryClassifierName = this.binaryClassifier.getName();
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

		final Path filePath = binaryClassifierFolderPath.resolve(binaryClassifierName + "_" + featureExtractorName + "_" + frameSize + "_" + errorClassName + "_" + id + ".yaml");
		log.info("Created FilePath");

		final BinaryClassifierConfiguration config = new BinaryClassifierConfiguration(filePath, binaryClassifierName, featureExtractorName, frameSize, errorClassName, id);
		log.info("Created BinaryClassifierConfiguration.");

		final IBinaryClassification classification = this.binaryClassifier.train(positive, negative, config);
		log.info("Trained Binary Classifier with " + positive + " positive and " + negative + " negative samples.");

		classification.save(filePath.toString());
		log.info("Saved File to FileSystem.");

		return classification;
	}

}
