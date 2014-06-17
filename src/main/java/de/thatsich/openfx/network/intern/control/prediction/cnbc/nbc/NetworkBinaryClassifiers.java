package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.Log;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import de.thatsich.openfx.classification.intern.control.command.commands.CreateTrainedBinaryClassifierCommand;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import javafx.application.Platform;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkBinaryClassifiers implements INBC
{
	private final List<ITrainedBinaryClassifier> trainedBinaryClassifier;
	private final Fuser fuser;
	private final String uniqueErrorClassName;
	private final List<IFeature> trainedFeatures;

	@Inject private IBinaryClassifiers binaryClassifiers;
	@Inject private IClassificationCommandProvider provider;
	@Inject private ITrainedClassifiers binaryClassifications;
	@Inject private Log log;

	@Inject
	public NetworkBinaryClassifiers(@Assisted String uniqueErrorClassName, @Assisted List<ITrainedBinaryClassifier> bcs, Fuser fuser)
	{
		this.uniqueErrorClassName = uniqueErrorClassName;
		this.trainedFeatures = new LinkedList<>();
		this.trainedBinaryClassifier = bcs;

		this.fuser = fuser;
	}

	@Override
	public List<ITrainedBinaryClassifier> getTrainedBinaryClassifier()
	{
		return this.trainedBinaryClassifier;
	}

	/**
	 * If new BC was added, then it needs to take all old inputed faetures
	 * and train the new BC with them.
	 *
	 * Example: 1 new BC, 3 old features, results in 3 new trained BCs
	 *
	 * @param bc newly added bc
	 */
	@Override
	public void addBinaryClassifier(IBinaryClassifier bc) throws Exception
	{
		if (this.binaryClassifiers.list().contains(bc)) return;

		this.binaryClassifiers.list().add(bc);

		for (IFeature trainedFeature : this.trainedFeatures)
		{
			final CreateTrainedBinaryClassifierCommand command = this.provider.createTrainBinaryClassifierCommand(bc, trainedFeature);
			final ITrainedBinaryClassifier trained = command.call();
			this.trainedBinaryClassifier.add(trained);

			Platform.runLater(() -> this.binaryClassifications.list().add(trained));
		}
	}

	@Override
	public void addFeature(IFeature feature) throws Exception
	{
		this.trainedFeatures.add(feature);
		this.log.info("Adding feature " + feature);

		try
		{
			this.log.info("Training new BCs through the list of size " + this.binaryClassifiers.list().size());
			for (IBinaryClassifier binaryClassifier : this.binaryClassifiers.list())
			{
				final CreateTrainedBinaryClassifierCommand command = this.provider.createTrainBinaryClassifierCommand(binaryClassifier, feature);
				final ITrainedBinaryClassifier trained = command.call();

				this.trainedBinaryClassifier.add(trained);
				Platform.runLater(() -> this.binaryClassifications.list().add(trained));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.log.info("Trained additional BCs for new feature " + feature);
	}

	@Override
	public double predict(IFeature f)
	{
		final String featureErrorName = f.className().get();
		final String featureExtractorName = f.extractorName().get();

		this.log.info("Predicting feature " + f + " over " + this.trainedBinaryClassifier.size() + " trained BCs.");
		final List<Double> values = new LinkedList<>();
		for (ITrainedBinaryClassifier trained : this.trainedBinaryClassifier)
		{
			final String trainedErrorName = trained.errorName().get();
			final String trainedExtractorName = trained.extractorName().get();

			this.log.info("Trying to match " + trainedErrorName + " = " + featureErrorName + " | " + trainedExtractorName + " = " + featureExtractorName);

			final boolean sameErrorName = trainedErrorName.equals(featureErrorName);
			final boolean sameExtractorName = trainedExtractorName.equals(featureExtractorName);

			if (sameErrorName && sameExtractorName)
			{
				this.log.info("Found matching error and extractor names");
				this.log.info("Using " + trained);

				for (IFeatureVector fv : f.vectors())
				{
					final double prediction = trained.predict(fv);
					values.add(prediction);
				}
			}
		}
		this.log.info("Finished predictions (" + values.size() + ")");

		final double fused = this.fuser.predict(values);
		this.log.info("Fused " + fused);

		return fused;
	}

	@Override
	public String getUniqueErrorClassName()
	{
		return this.uniqueErrorClassName;
	}
}
