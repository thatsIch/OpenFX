package de.thatsich.bachelor.errorgeneration.api.guice;

import com.google.inject.Scopes;
import de.thatsich.bachelor.errorgeneration.api.view.IErrorDisplayView;
import de.thatsich.bachelor.errorgeneration.api.model.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.view.IErrorInputView;
import de.thatsich.bachelor.errorgeneration.api.view.IErrorListView;
import de.thatsich.bachelor.errorgeneration.api.model.IErrorState;
import de.thatsich.bachelor.errorgeneration.intern.model.ErrorEntries;
import de.thatsich.bachelor.errorgeneration.intern.model.ErrorGenerators;
import de.thatsich.bachelor.errorgeneration.intern.model.ErrorState;
import de.thatsich.bachelor.errorgeneration.intern.control.command.service.ErrorConfigService;
import de.thatsich.bachelor.errorgeneration.intern.view.ErrorDisplayView;
import de.thatsich.bachelor.errorgeneration.intern.view.ErrorInputView;
import de.thatsich.bachelor.errorgeneration.intern.view.ErrorListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 *
 * @author Minh
 */
public class ErrorWiringModule extends AWiringModule
{

	@Override
	protected void bindModule()
	{
		super.bind(ErrorWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(ErrorConfigService.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IErrorDisplayView.class).to(ErrorDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IErrorInputView.class).to(ErrorInputView.class).in(Scopes.SINGLETON);
		super.bind(IErrorListView.class).to(ErrorListView.class).in(Scopes.SINGLETON);
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
		super.bind(IErrorEntries.class).to(ErrorEntries.class).in(Scopes.SINGLETON);
		super.bind(IErrorGenerators.class).to(ErrorGenerators.class).in(Scopes.SINGLETON);
		super.bind(IErrorState.class).to(ErrorState.class).in(Scopes.SINGLETON);
	}
}
