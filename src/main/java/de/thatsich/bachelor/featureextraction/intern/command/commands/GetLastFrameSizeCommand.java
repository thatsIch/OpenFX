package de.thatsich.bachelor.featureextraction.intern.command.commands;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.intern.services.FeatureConfigService;
import de.thatsich.core.javafx.ACommand;

public class GetLastFrameSizeCommand extends ACommand<Integer>
{

	// Injects
	@Inject
	private FeatureConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.getLastFrameSizeInt();
	}
}
