package de.thatsich.bachelor.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.featureextraction.intern.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastFeatureExtractorIndexCommand extends ACommand<Void>
{

	// Properties
	private final int lastFeatureExtractorIndex;

	// Injects
	@Inject
	private FeatureConfigService config;

	@Inject
	protected SetLastFeatureExtractorIndexCommand(@Assisted int lastFeatureExtractorIndex)
	{
		this.lastFeatureExtractorIndex = lastFeatureExtractorIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastFeatureExtractorIndex(this.lastFeatureExtractorIndex);

		return null;
	}
}