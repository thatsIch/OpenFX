package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;

public class SetLastFeatureIndexCommand extends ACommand<Void>
{
	// Properties
	private final int lastFeatureVectorIndex;
	private final FeatureConfigService config;

	@Inject
	protected SetLastFeatureIndexCommand(@Assisted int lastFeatureVectorIndex, final FeatureConfigService config)
	{
		this.lastFeatureVectorIndex = lastFeatureVectorIndex;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastFeatureIndex(this.lastFeatureVectorIndex);

		return null;
	}
}