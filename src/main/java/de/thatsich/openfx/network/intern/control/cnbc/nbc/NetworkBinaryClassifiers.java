package de.thatsich.openfx.network.intern.control.cnbc.nbc;

import de.thatsich.openfx.classification.api.control.entity.IBinaryClassification;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;

import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkBinaryClassifiers implements INBC
{
	private final List<IBinaryClassifier> binaryClassifiers = new LinkedList<>();
	private final Fuser fuser = new Fuser();
	private final String uniqueImageClass;
	private double fuserOutput;

	public NetworkBinaryClassifiers(final String uniqueImageClass)
	{
		this.uniqueImageClass = uniqueImageClass;
	}

	public void addBinaryClassifier(IBinaryClassifier bc)
	{
		this.binaryClassifiers.add(bc);
	}

	public void train(List<IFeatures> sets)
	{
		this.trainBinaryClassifiers(sets);
		this.trainFuser();
	}

	private List<IBinaryClassification> trainBinaryClassifiers(List<IFeatures> featureVectorSets)
	{
		final List<IBinaryClassification> classifications = new LinkedList<>();

		for (IBinaryClassifier bc : this.binaryClassifiers)
		{
			final IBinaryClassification classification = bc.train(null, null, null /* TODO args */);
			classifications.add(classification);
		}

		return classifications;
	}

	private void trainFuser()
	{
		this.fuser.train();
	}

	public void addNewFeatureVectorSet(final IFeature fvs) {}

	public void addNewFeature(final FeatureVector fv)
	{

	}

	@Override
	public String getUnqiueImageClass()
	{
		return this.uniqueImageClass;
	}

	@Override
	public void evolve()
	{

	}

	@Override
	public void addFeature()
	{

	}

	@Override
	public void removeFeature()
	{

	}

	@Override
	public double getFuserOutput()
	{
		return this.fuserOutput;
	}
}
