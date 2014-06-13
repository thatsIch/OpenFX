package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import com.google.inject.Inject;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.classification.api.control.entity.ITraindBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.command.commands.CreateTrainedBinaryClassifierCommand;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationFileStorageService;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkBinaryClassifiers implements INBC
{
	private final Set<IBinaryClassifier> binaryClassifiers;
	private final List<ITraindBinaryClassifier> trainedBinaryClassifier;
	private final Fuser fuser;
	private final String uniqueErrorClassName;
	private final List<IFeature> trainedFeatures;
	@Inject private ClassificationFileStorageService storage;

	public NetworkBinaryClassifiers(String uniqueErrorClassName)
	{
		this.uniqueErrorClassName = uniqueErrorClassName;
		this.binaryClassifiers = new HashSet<>();
		this.trainedFeatures = new LinkedList<>();
		this.trainedBinaryClassifier = new LinkedList<>();

		this.fuser = new Fuser();
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
		if (this.binaryClassifiers.contains(bc)) return;

		this.binaryClassifiers.add(bc);

		for (IFeature trainedFeature : this.trainedFeatures)
		{
			final CreateTrainedBinaryClassifierCommand command = new CreateTrainedBinaryClassifierCommand(bc, trainedFeature, this.storage);
			final ITraindBinaryClassifier trained = command.call();
			this.trainedBinaryClassifier.add(trained);
		}
	}

	@Override
	public void addFeature(IFeature feature) throws Exception
	{
		this.trainedFeatures.add(feature);

		for (IBinaryClassifier binaryClassifier : this.binaryClassifiers)
		{
			final CreateTrainedBinaryClassifierCommand command = new CreateTrainedBinaryClassifierCommand(binaryClassifier, feature, this.storage);
			final ITraindBinaryClassifier trained = command.call();
			this.trainedBinaryClassifier.add(trained);
		}
	}

	@Override
	public double predict(IFeatureVector fv)
	{
		final List<Double> values = new LinkedList<>();
		for (ITraindBinaryClassifier trained : this.trainedBinaryClassifier)
		{
			final double prediction = trained.predict(fv);
			values.add(prediction);
		}

		return this.fuser.predict(values);
	}

	@Override
	public String getUniqueErrorClassName()
	{
		return this.uniqueErrorClassName;
	}
}
