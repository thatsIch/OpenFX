package de.thatsich.openfx.featureextraction.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;

import java.util.List;
import java.util.Optional;

/**
 * Handler for what should happen if the Command was successfull
 * for extracting the featurevector
 *
 * @author Minh
 */
public class ExtractFeatureSucceededHandler extends ACommandHandler<IFeature>
{
	@Inject private IFeatures features;

	@Override
	public void handle(IFeature feature)
	{
		final IFeature merge = this.merge(this.features, feature);
		this.log.info("Merged Feature into Database.");

		this.features.selectedFeature().set(merge);
		this.log.info("Set current to selected Feature.");
	}

	private IFeature merge(IFeatures features, IFeature feature)
	{
		final Optional<IFeature> maybeMatch = this.getMatchingFeature(features, feature);
		if (maybeMatch.isPresent())
		{
			final IFeature match = maybeMatch.get();
			final List<IFeatureVector> vectors = feature.vectors();

			match.vectors().addAll(vectors);
			features.list().remove(match);
			features.list().add(match);

			return match;
		}
		else
		{
			features.list().add(feature);

			return feature;
		}
	}

	private Optional<IFeature> getMatchingFeature(IFeatures features, IFeature feature)
	{
		for (IFeature iFeature : features.list())
		{
			final boolean sameSize = iFeature.tileSize() == feature.tileSize();
			final boolean sameClass = iFeature.className().equals(feature.className());
			final boolean sameExtractor = iFeature.extractorName().equals(feature.extractorName());

			if (sameSize && sameClass && sameExtractor)
			{
				this.log.info("Found matching feature.");
				return Optional.of(iFeature);
			}
		}

		return Optional.empty();
	}
}
