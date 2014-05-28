package de.thatsich.bachelor.featureextraction.intern.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.featureextraction.intern.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class SetLastFeatureVectorIndexCommand extends ACommand<Void>
{

	// Properties
	private final int lastFeatureVectorIndex;

	// Injects
	@Inject
	private FeatureConfigService config;

	@Inject
	protected SetLastFeatureVectorIndexCommand(@Assisted int lastFeatureVectorIndex)
	{
		this.lastFeatureVectorIndex = lastFeatureVectorIndex;
	}

	@Override
	protected Void call() throws Exception
	{
		config.setLastFeatureVectorIndexInt(this.lastFeatureVectorIndex);

		return null;
	}
}