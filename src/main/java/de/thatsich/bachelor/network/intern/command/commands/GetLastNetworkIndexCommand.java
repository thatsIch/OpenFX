package de.thatsich.bachelor.network.intern.command.commands;

import com.google.inject.Inject;
import de.thatsich.bachelor.network.intern.service.NetworkConfigService;
import de.thatsich.core.javafx.ACommand;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class GetLastNetworkIndexCommand extends ACommand<Integer>
{
	@Inject private NetworkConfigService config;

	@Override
	protected Integer call() throws Exception
	{
		return this.config.getLastNetworkIndex();
	}
}
