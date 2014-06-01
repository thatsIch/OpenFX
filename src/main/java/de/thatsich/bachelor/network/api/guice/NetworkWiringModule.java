package de.thatsich.bachelor.network.api.guice;

import com.google.inject.Scopes;
import de.thatsich.bachelor.network.api.view.INetworkDisplayView;
import de.thatsich.bachelor.network.api.view.INetworkInputView;
import de.thatsich.bachelor.network.api.view.INetworkListView;
import de.thatsich.bachelor.network.api.model.INetworkState;
import de.thatsich.bachelor.network.api.model.INetworks;
import de.thatsich.bachelor.network.intern.model.NetworkState;
import de.thatsich.bachelor.network.intern.model.Networks;
import de.thatsich.bachelor.network.intern.control.command.service.NetworkConfigService;
import de.thatsich.bachelor.network.intern.control.command.service.NetworkFileStorageService;
import de.thatsich.bachelor.network.intern.view.NetworkDisplayView;
import de.thatsich.bachelor.network.intern.view.NetworkInputView;
import de.thatsich.bachelor.network.intern.view.NetworkListView;
import de.thatsich.core.guice.AWiringModule;

/**
 * @author thatsIch
 * @since 31.05.2014.
 */
public class NetworkWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(NetworkWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(NetworkFileStorageService.class).in(Scopes.SINGLETON);
		super.bind(NetworkConfigService.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(INetworkDisplayView.class).to(NetworkDisplayView.class).in(Scopes.SINGLETON);
		super.bind(INetworkInputView.class).to(NetworkInputView.class).in(Scopes.SINGLETON);
		super.bind(INetworkListView.class).to(NetworkListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController()
	{

	}

	@Override
	protected void bindCommand()
	{

	}

	@Override
	protected void bindModel()
	{
		super.bind(INetworks.class).to(Networks.class).in(Scopes.SINGLETON);
		super.bind(INetworkState.class).to(NetworkState.class).in(Scopes.SINGLETON);
	}
}
