package de.thatsich.openfx.classification.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import org.opencv.core.MatOfFloat;

import java.util.UUID;


public class CreateTrainedBinaryClassifierCommand extends ACommand<IBinaryClassification>
{
	// Properties
	private final IBinaryClassifier binaryClassifier;
	private final IFeature feature;
	private final ClassificationFileStorageService storage;

	@Inject
	public CreateTrainedBinaryClassifierCommand(
		@Assisted IBinaryClassifier classifier, @Assisted IFeature feature, ClassificationFileStorageService storage
	)
	{
		this.binaryClassifier = classifier;
		this.feature = feature;
		this.storage = storage;
	}

	@Override
	protected IBinaryClassification call() throws Exception
	{
		final String binaryClassifierName = this.binaryClassifier.getName();
		final String featureExtractorName = this.feature.extractorName().get();
		final int tileSize = this.feature.tileSize().get();
		final String errorClassName = this.feature.className().get();
		final String id = UUID.randomUUID().toString();

		final MatOfFloat positive = new MatOfFloat();
		final MatOfFloat negative = new MatOfFloat();
		this.log.info("Prepared all data for Training.");

		for (IFeatureVector vector : this.feature.vectors())
		{
			final float[] floatArray = new float[vector.vector().size()];
			int index = 0;
			for (float f : vector.vector())
			{
				floatArray[index] = f;
				index++;
			}

			if (vector.isPositive().get())
			{
				positive.push_back(new MatOfFloat(floatArray).t());
			}
			else
			{
				negative.push_back(new MatOfFloat(floatArray).t());
			}
		}
		this.log.info("Prepared Negative and Positive DataSets.");

		final BinaryClassificationConfig config = new BinaryClassificationConfig(binaryClassifierName, featureExtractorName, tileSize, errorClassName, id);
		this.log.info("Created BinaryClassificationConfig.");

		final IBinaryClassification classification = this.binaryClassifier.train(positive, negative, config);
		this.log.info("Trained Binary Classifier with " + positive + " positive and " + negative + " negative samples.");

		this.storage.create(classification);
		this.log.info("Saved File to FileSystem.");

		return classification;
	}

}
