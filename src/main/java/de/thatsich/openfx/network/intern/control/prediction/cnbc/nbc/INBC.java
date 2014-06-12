package de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc;

import de.thatsich.openfx.classification.api.control.entity.IBinaryClassifier;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
public interface INBC
{
	void addBinaryClassifier(IBinaryClassifier bc) throws Exception;

	void addFeature(IFeature feature) throws Exception;

	double predict(IFeatureVector fv);

	String getUniqueErrorClassName();
}
