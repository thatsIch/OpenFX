package de.thatsich.openfx.featureextraction.intern.control.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureFileStorageService;

import java.io.IOException;
import java.util.Optional;

/**
 * Handler for what should happen if the Command was successfull
 * for extracting the featurevector
 *
 * @author Minh
 */
public class ExtractFeatureSucceededHandler extends ACommandHandler<IFeature>
{
	private final IFeatures features;
	private final FeatureFileStorageService storage;

	@Inject
	public ExtractFeatureSucceededHandler(IFeatures features, FeatureFileStorageService storage)
	{
		this.features = features;
		this.storage = storage;
	}

	@Override
	public void handle(IFeature feature)
	{
		final IFeature merge = this.merge(this.features, feature);
		this.log.info("Merged Feature into Database.");

		try
		{
			this.storage.create(merge);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.features.selected().set(merge);
		this.log.info("Set current to selected Feature.");
	}

	private IFeature merge(IFeatures features, IFeature feature)
	{
		final Optional<IFeature> maybeMatch = this.getMatchingFeature(features, feature);
		if (maybeMatch.isPresent())
		{
			final IFeature match = maybeMatch.get();
			match.merge(feature);

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
			final boolean sameSize = iFeature.tileSize().get() == feature.tileSize().get();
			final boolean sameClass = iFeature.className().get().equals(feature.className().get());
			final boolean sameExtractor = iFeature.extractorName().get().equals(feature.extractorName().get());

			if (sameSize && sameClass && sameExtractor)
			{
				this.log.info("Found matching feature.");
				return Optional.of(iFeature);
			}
		}

		return Optional.empty();
	}
}
