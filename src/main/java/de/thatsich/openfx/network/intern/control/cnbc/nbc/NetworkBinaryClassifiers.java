package de.thatsich.openfx.network.intern.control.cnbc.nbc;

import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.classification.intern.control.classifier.core.IBinaryClassifier;
import de.thatsich.openfx.featureextraction.api.control.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.openfx.featureextraction.api.model.IFeatureVectorSets;

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
	private final String errorClass;
	private double fuserOutput;

	public NetworkBinaryClassifiers(final String errorClass)
	{
		this.errorClass = errorClass;
	}
	//	private final uniqueImageClass;

	public void addBinaryClassifier(IBinaryClassifier bc)
	{
		this.binaryClassifiers.add(bc);
	}


	public void train(List<IFeatureVectorSets> sets)
	{
		this.trainBinaryClassifiers(sets);
		this.trainFuser();
	}

	private List<IBinaryClassification> trainBinaryClassifiers(List<IFeatureVectorSets> featureVectorSets)
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

	public void addNewFeatureVectorSet(final FeatureVectorSet fvs) {}

	public void addNewFeature(final FeatureVector fv)
	{

	}

	@Override
	public String getUnqiueImageClass()
	{
		return null;
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
