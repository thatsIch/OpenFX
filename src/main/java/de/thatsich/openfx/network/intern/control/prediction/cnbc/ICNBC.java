package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import javafx.util.Pair;

import java.util.List;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
public interface ICNBC
{
	List<INBC> getNbcs();

	void addFeature(IFeature feature) throws Exception;

	List<Pair<String, Double>> predict(IFeatureVector fv);
}
