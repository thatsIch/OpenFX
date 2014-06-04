package de.thatsich.openfx.featureextraction.api.control.entity;

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

	List<IFeatureVector> getFeatureVectors();
}
