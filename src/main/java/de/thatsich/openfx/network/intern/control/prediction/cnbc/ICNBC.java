package de.thatsich.openfx.network.intern.control.prediction.cnbc;

import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import javafx.util.Pair;

import java.util.List;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
public interface ICNBC
{
	void removeFeature(IFeature feature);

	void addFeature(IFeature feature);

	void addClass(String clazz);

	void adeptToNewClass();

	List<Pair<String, Double>> predict(List<IFeature> features);

	void train(List<IFeature> features);
}
