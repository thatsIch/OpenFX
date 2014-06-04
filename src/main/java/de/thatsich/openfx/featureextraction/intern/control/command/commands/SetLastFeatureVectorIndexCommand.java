package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;

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