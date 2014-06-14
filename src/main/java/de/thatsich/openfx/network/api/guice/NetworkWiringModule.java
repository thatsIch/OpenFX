package de.thatsich.openfx.network.api.guice;

import com.google.inject.Scopes;
import de.thatsich.core.guice.AWiringModule;
import de.thatsich.openfx.network.api.control.entity.INetworkSpace;
import de.thatsich.openfx.network.api.model.INetworkState;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.api.view.INetworkDisplayView;
import de.thatsich.openfx.network.api.view.INetworkInputView;
import de.thatsich.openfx.network.api.view.INetworkListView;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
import de.thatsich.openfx.network.intern.control.command.service.NetworkConfigService;
import de.thatsich.openfx.network.intern.control.command.service.NetworkFileStorageService;
import de.thatsich.openfx.network.intern.control.prediction.NetworkSpace;
import de.thatsich.openfx.network.intern.model.NetworkState;
import de.thatsich.openfx.network.intern.model.Networks;
import de.thatsich.openfx.network.intern.view.NetworkDisplayView;
import de.thatsich.openfx.network.intern.view.NetworkInputView;
import de.thatsich.openfx.network.intern.view.NetworkListView;

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
	protected void bindModel()
	{
		super.bind(INetworks.class).to(Networks.class).in(Scopes.SINGLETON);
		super.bind(INetworkState.class).to(NetworkState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(INetworkDisplayView.class).to(NetworkDisplayView.class).in(Scopes.SINGLETON);
		super.bind(INetworkInputView.class).to(NetworkInputView.class).in(Scopes.SINGLETON);
		super.bind(INetworkListView.class).to(NetworkListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindControl()
	{
		super.bind(INetworkSpace.class).to(NetworkSpace.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindCommand()
	{
		super.bind(NetworkInitCommander.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService()
	{
		super.bind(NetworkFileStorageService.class).in(Scopes.SINGLETON);
		super.bind(NetworkConfigService.class).in(Scopes.SINGLETON);
	}
}
