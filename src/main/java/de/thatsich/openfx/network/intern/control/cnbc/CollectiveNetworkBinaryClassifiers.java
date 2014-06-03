package de.thatsich.openfx.network.intern.control.cnbc;

import com.google.inject.Inject;
import de.thatsich.openfx.featureextraction.api.control.FeatureVector;
import de.thatsich.openfx.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.openfx.network.intern.control.cnbc.nbc.NetworkBinaryClassifiers;
import de.thatsich.core.Log;

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
	final private List<NetworkBinaryClassifiers> nbcs;
	final private Set<String> uniqueErrorClasses;
	final private ClassSelection classSelection;

	final private Log log;

	@Inject
	public CollectiveNetworkBinaryClassifiers(ClassSelection classSelection, Log log)
	{
		this.nbcs = new LinkedList<>();
		this.uniqueErrorClasses = new HashSet<>();
		this.classSelection = classSelection;

		this.log = log;
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
	 * @param fvs FeatureVectorSet
	 */
	private void addNewFeatureVectorSet(FeatureVectorSet fvs) {
		this.nbcs.forEach(nbc -> nbc.addNewFeatureVectorSet(fvs));
	}

	/**
	 * Update underlying NBCs with new FV
	 *
	 * @param fv new featurevector
	 */
	private void addNewFeatureVector(FeatureVector fv)
	{
		this.nbcs.forEach((nbc) -> nbc.addNewFeature(fv));
	}

	private void trainNetworkBinaryClassifiers() {

	}

	public void initialEvolution(List<FeatureVectorSet> fvs)
	{
		fvs.forEach(this::addNewFeatureVectorSet);
		this.trainNetworkBinaryClassifiers();
	}

	public void incrementalEvolution()
	{

	}

	@Override
	public void removeFeature()
	{

	}

	@Override
	public void addFeature()
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
}
