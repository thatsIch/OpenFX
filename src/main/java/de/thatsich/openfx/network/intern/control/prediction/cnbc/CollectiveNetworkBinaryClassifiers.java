package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import com.google.inject.Inject;
import de.thatsich.core.Log;
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
	private final Log log;
	private final List<INBC> nbcs;
	private final Set<String> uniqueErrorClasses;

	@Inject
	public CollectiveNetworkBinaryClassifiers(Log log)
	{
		this.log = log;
		this.nbcs = new LinkedList<>();
		this.uniqueErrorClasses = new HashSet<>();
	}

	public CollectiveNetworkBinaryClassifiers(List<INBC> nbcs, Set<String> uniqueErrorClasses, Log log)
	{
		this.log = log;
		this.nbcs = nbcs;
		this.uniqueErrorClasses = uniqueErrorClasses;
	}

	@Override
	public List<INBC> getNbcs()
	{
		return this.nbcs;
	}

	@Override
	public void addFeature(IFeature feature) throws Exception
	{
		final String potUniqueErrorClass = feature.getConfig().toString();
		if (!this.uniqueErrorClasses.contains(potUniqueErrorClass))
		{
			this.uniqueErrorClasses.add(potUniqueErrorClass);

			final INBC nbc = new NetworkBinaryClassifiers(potUniqueErrorClass, new LinkedList<>());
			this.nbcs.add(nbc);
			this.log.info("Created new NBC for unique errorclass " + potUniqueErrorClass);
		}

		for (INBC nbc : this.nbcs)
		{
			nbc.addFeature(feature);
		}
	}

	@Override
	public List<Pair<String, Double>> predict(IFeatureVector fv)
	{
		final List<Pair<String, Double>> pairs = new LinkedList<>();

		for (INBC nbc : this.nbcs)
		{
			final double predict = nbc.predict(fv);
			final String name = nbc.getUniqueErrorClassName();
			final Pair<String, Double> pair = new Pair<>(name, predict);

			pairs.add(pair);
			this.log.info("Predicted " + pair);
		}

		return pairs;
	}
}
