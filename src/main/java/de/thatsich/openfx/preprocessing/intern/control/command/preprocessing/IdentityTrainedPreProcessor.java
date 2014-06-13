package de.thatsich.openfx.preprocessing.intern.control.command.preprocessing;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.intern.control.entity.Feature;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureConfig;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.ATrainedPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessing.core.TrainedPreProcessorConfig;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.encog.neural.networks.BasicNetwork;

import java.util.List;

/**
 * @author thatsIch
 * @since 29.05.2014.
 */
public class IdentityTrainedPreProcessor extends ATrainedPreProcessor
{
	private ReadOnlyObjectProperty<BasicNetwork> networkProperty;

	@Inject
	public IdentityTrainedPreProcessor(@Assisted BasicNetwork network, @Assisted TrainedPreProcessorConfig config)
	{
		super(config);
		this.networkProperty = new ReadOnlyObjectWrapper<>(network);
	}

	@Override
	public IFeature preprocess(IFeature feature)
	{
		final FeatureConfig config = feature.getConfig();
		final List<IFeatureVector> vectors = feature.vectors();

		final String className = config.className.get();
		final String extractorName = config.extractorName.get();
		final String preProcessorName = this.nameProperty().get();
		final int tileSize = config.tileSize.get();

		final FeatureConfig newConfig = new FeatureConfig(className, extractorName, preProcessorName, tileSize);

		final IFeature newFeature = new Feature(newConfig, vectors);

		return newFeature;
	}

	@Override
	public ReadOnlyObjectProperty<BasicNetwork> networkProperty()
	{
		return this.networkProperty;
	}
}
