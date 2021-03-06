package de.thatsich.openfx.network.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.network.intern.control.command.service.NetworkConfigService;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class SetLastNetworkIndexCommand extends ACommand<Void>
{
	private final int index;

	@Inject private NetworkConfigService config;

	@Inject
	protected SetLastNetworkIndexCommand(@Assisted int index)
	{
		this.index = index;
	}

	@Override
	protected Void call() throws Exception
	{
		this.config.setLastNetworkIndex(this.index);

		return null;
	}
}
