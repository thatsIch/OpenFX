package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassifierConfiguration;
import de.thatsich.openfx.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import org.opencv.core.MatOfFloat;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


public class TrainBinaryClassifierCommand extends ACommand<IBinaryClassification>
{
	// Properties
	private final Path binaryClassifierFolderPath;
	private final IBinaryClassifier binaryClassifier;
	private final IFeature selectedFeatureVector;
	private final List<IFeature> featureVectorList;

	@Inject
	public TrainBinaryClassifierCommand(@Assisted Path binaryClassifierFolderPath, @Assisted IBinaryClassifier classifier, @Assisted IFeature selected, @Assisted List<IFeature> all)
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
		final String featureExtractorName = this.selectedFeatureVector.getExtractorName();
		final int frameSize = this.selectedFeatureVector.getFrameSize();
		final String errorClassName = this.selectedFeatureVector.getClassName();
		final String id = UUID.randomUUID().toString();

		final MatOfFloat positive = new MatOfFloat();
		final MatOfFloat negative = new MatOfFloat();
		this.log.info("Prepared all data for Training.");

		// run through all FeatureVectorSets matching same categories
		// (same FrameSize, same Extractor, same ErrorClass)
		// which is not the selected one and their data to train
		// extract all float lists and transform them into MatOfFloats
		// use .t() on them to transpose them
		for (IFeature feature : this.featureVectorList)
		{
			// select only with same FeatureExtractor and FrameSize
			if (feature.getExtractorName().equals(featureExtractorName) && feature.getFrameSize() == frameSize)
			{
				for (IFeatureVector vector : feature.getFeatureVectors())
				{
					final float[] floatArray = new float[vector.vector().size()];
					int index = 0;
					for (float f : vector.vector())
					{
						floatArray[index] = f;
						index++;
					}

					if (vector.isPositive())
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
		this.log.info("Prepared Negative and Positive DataSets.");

		final Path filePath = this.binaryClassifierFolderPath.resolve(binaryClassifierName + "_" + featureExtractorName + "_" + frameSize + "_" + errorClassName + "_" + id + ".yaml");
		this.log.info("Created FilePath");

		final BinaryClassifierConfiguration config = new BinaryClassifierConfiguration(filePath, binaryClassifierName, featureExtractorName, frameSize, errorClassName, id);
		this.log.info("Created BinaryClassifierConfiguration.");

		final IBinaryClassification classification = this.binaryClassifier.train(positive, negative, config);
		this.log.info("Trained Binary Classifier with " + positive + " positive and " + negative + " negative samples.");

		classification.save(filePath.toString());
		this.log.info("Saved File to FileSystem.");

		return classification;
	}

}
