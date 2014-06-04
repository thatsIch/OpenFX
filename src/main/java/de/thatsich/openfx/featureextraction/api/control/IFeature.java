package de.thatsich.openfx.featureextraction.api.control;

import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVector;

import java.nio.file.Path;
import java.util.List;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IFeature
{
	Path getPath();

	String getExtractorName();

	String getClassName();

	int getFrameSize();

	List<FeatureVector> getFeatureVectors();
}
