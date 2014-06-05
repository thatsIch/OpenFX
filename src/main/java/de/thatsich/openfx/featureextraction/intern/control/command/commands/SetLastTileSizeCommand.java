package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;

public class SetLastTileSizeCommand extends ACommand<Void>
{

	// Properties
	private final int lastFrameSize;

	// Injects
	@Inject
	private FeatureConfigService config;

	@Inject
	protected SetLastTileSizeCommand(@Assisted int lastFrameSize)
	{
		this.lastFrameSize = lastFrameSize;
	}

	@Override
	protected Void call() throws Exception
	{
		config.setLastTileSize(this.lastFrameSize);

		return null;
	}
}
