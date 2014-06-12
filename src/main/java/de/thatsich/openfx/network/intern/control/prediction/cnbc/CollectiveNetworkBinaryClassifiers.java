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
	private final Log log;
	private final List<INBC> nbcs;
	private final Set<String> uniqueErrorClasses;
	private List<IBinaryClassifier> binaryClassifiers;

	@Inject
	public CollectiveNetworkBinaryClassifiers(Log log)
	{
		this.log = log;
		this.nbcs = new LinkedList<>();
		this.uniqueErrorClasses = new HashSet<>();
	}

	public void addBinaryClassifier(IBinaryClassifier binaryClassifier)
	{
		this.nbcs.forEach(nbc -> {
			try
			{
				nbc.addBinaryClassifier(binaryClassifier);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		});
	}

	@Override
	public void addFeature(IFeature feature) throws Exception
	{
		final String potUniqueErrorClass = feature.getConfig().toString();
		if (!this.uniqueErrorClasses.contains(potUniqueErrorClass))
		{
			this.uniqueErrorClasses.add(potUniqueErrorClass);

			final INBC nbc = new NetworkBinaryClassifiers(potUniqueErrorClass);
			this.nbcs.add(nbc);
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
		}

		return pairs;
	}
}
