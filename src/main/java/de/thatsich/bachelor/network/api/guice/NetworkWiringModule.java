package de.thatsich.bachelor.network.api.guice;

import com.google.inject.Scopes;
import de.thatsich.bachelor.network.api.core.INetworkDisplayView;
import de.thatsich.bachelor.network.api.core.INetworkInputView;
import de.thatsich.bachelor.network.api.core.INetworkListView;
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

	}
}
