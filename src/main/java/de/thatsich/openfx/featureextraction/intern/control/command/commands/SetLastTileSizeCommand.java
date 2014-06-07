package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureConfigService;

public class SetLastTileSizeCommand extends ACommand<Void>
{

	// Properties
	private final int lastFrameSize;
	private final FeatureConfigService config;

	@Inject
	protected SetLastTileSizeCommand(@Assisted int lastFrameSize, FeatureConfigService config)
	{
		this.lastFrameSize = lastFrameSize;
		this.config = config;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastTileSize(this.lastFrameSize);

		return null;
	}
}
