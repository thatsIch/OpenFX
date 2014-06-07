package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;

public class SetLastFeatureExtractorIndexCommand extends ACommand<Void>
{
	private final int lastFeatureExtractorIndex;
	private final FeatureConfigService config;

	@Inject
	protected SetLastFeatureExtractorIndexCommand(@Assisted int lastFeatureExtractorIndex, FeatureConfigService config)
	{
		this.lastFeatureExtractorIndex = lastFeatureExtractorIndex;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastFeatureExtractorIndex(this.lastFeatureExtractorIndex);

		return null;
	}
}