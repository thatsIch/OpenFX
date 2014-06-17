package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.Log;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import de.thatsich.openfx.network.intern.control.provider.INetworkCommandProvider;
import javafx.util.Pair;

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

	@Inject private INetworkCommandProvider provider;

	@Inject
	public CollectiveNetworkBinaryClassifiers(@Assisted List<INBC> nbcs, @Assisted Set<String> uniqueErrorClasses, Log log)
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
		final String potUniqueErrorClass = feature.getConfig().className.get();

		this.log.info("Checking if " + potUniqueErrorClass + " is already in uniqueErrorClasses " + this.uniqueErrorClasses);
		if (!this.uniqueErrorClasses.contains(potUniqueErrorClass))
		{
			this.log.info("Its not.");
			this.uniqueErrorClasses.add(potUniqueErrorClass);

			final INBC nbc = this.provider.createNetworkBinaryClassifiers(potUniqueErrorClass, new LinkedList<>());
			this.nbcs.add(nbc);
			this.log.info("Created new NBC " + nbc + " for unique errorclass " + potUniqueErrorClass);
		}

		this.log.info("NBC Size " + this.nbcs.size());

		for (INBC nbc : this.nbcs)
		{
			if (feature.className().get().contentEquals(nbc.getUniqueErrorClassName()))
			{
				this.log.info("Adding feature " + feature + " to NBC " + nbc);
				nbc.addFeature(feature);
				this.log.info("Added feature " + feature + " to NBC " + nbc);
			}
		}
	}

	@Override
	public List<Pair<String, Double>> predict(IFeature f)
	{
		final List<Pair<String, Double>> pairs = new LinkedList<>();
		this.log.info("Predicting over " + this.nbcs.size() + " NBCs");

		for (INBC nbc : this.nbcs)
		{
			final double predict = nbc.predict(f);
			final String name = nbc.getUniqueErrorClassName();
			final Pair<String, Double> pair = new Pair<>(name, predict);

			pairs.add(pair);
			this.log.info("Predicted " + pair);
		}

		return pairs;
	}
}
