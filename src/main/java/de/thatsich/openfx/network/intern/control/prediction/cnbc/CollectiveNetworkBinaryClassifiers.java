package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.NetworkBinaryClassifiers;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class CollectiveNetworkBinaryClassifiers implements ICNBC
{
	private final List<IBinaryClassifier> binaryClassifiers;
	final private Log log;
	final private List<INBC> nbcs;
	final private Set<String> uniqueErrorClasses;

	@Inject
	public CollectiveNetworkBinaryClassifiers(List<IBinaryClassifier> binaryClassifiers, Log log)
	{
		this.binaryClassifiers = binaryClassifiers;
		this.log = log;
		this.nbcs = new LinkedList<>();
		this.uniqueErrorClasses = new HashSet<>();
	}

	public List<INBC> getNbcs()
	{
		return this.nbcs;
	}

	/**
	 * When a new unique error class is created
	 * a new network of binary classifiers is added
	 *
	 * @param errorClass new error class
	 */
	public void addNewErrorClass(final String errorClass)
	{
		if (!this.uniqueErrorClasses.contains(errorClass))
		{
			final NetworkBinaryClassifiers nbc = new NetworkBinaryClassifiers(errorClass);
			this.nbcs.add(nbc);
			this.log.info("Added new NBC.");
		}
		else
		{
			this.log.info("Error Class already exists.");
		}
	}

	/**
	 * Initial Evolution
	 *
	 * @param fvs FeatureVectorSet
	 */
	private void addNewFeatureVectorSet(IFeature fvs)
	{
		this.nbcs.forEach(nbc -> nbc.addNewFeatureVectorSet(fvs));
	}

	/**
	 * Update underlying NBCs with new FV
	 *
	 * @param fv new featurevector
	 */
	private void addNewFeatureVector(IFeatureVector fv)
	{
		this.nbcs.forEach((nbc) -> nbc.addNewFeature(fv));
	}

	public void initialEvolution(List<IFeature> features)
	{
		features.forEach(this::addNewFeatureVectorSet);
		this.trainNetworkBinaryClassifiers();
	}

	private void trainNetworkBinaryClassifiers()
	{

	}

	public void incrementalEvolution()
	{

	}

	@Override
	public void removeFeature(IFeature feature)
	{

	}

	@Override
	public void addFeature(IFeature feature)
	{

	}

	@Override
	public void addClass(String clazz)
	{

	}

	@Override
	public void adeptToNewClass()
	{

	}

	@Override
	public List<Pair<String, Double>> predict(List<IFeature> features)
	{
		return null;
	}

	@Override
	public void train(List<IFeature> features)
	{

	}
}
