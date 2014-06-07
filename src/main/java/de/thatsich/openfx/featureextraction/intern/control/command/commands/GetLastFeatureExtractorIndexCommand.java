package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;

public class GetLastFeatureExtractorIndexCommand extends ACommand<Integer>
{

	// Injections
	@Inject
	private FeatureConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.getLastFeatureExtractorIndex();
	}
}