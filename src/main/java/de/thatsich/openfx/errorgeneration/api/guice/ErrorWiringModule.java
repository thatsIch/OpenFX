package de.thatsich.openfx.errorgeneration.api.guice;

import com.google.inject.Scopes;
import de.thatsich.core.guice.AWiringModule;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;
import de.thatsich.openfx.errorgeneration.api.model.IErrorState;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import de.thatsich.openfx.errorgeneration.api.view.IErrorDisplayView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorInputView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorListView;
import de.thatsich.openfx.errorgeneration.intern.control.command.ErrorInitCommander;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorConfigService;
import de.thatsich.openfx.errorgeneration.intern.control.command.service.ErrorFileStorageService;
import de.thatsich.openfx.errorgeneration.intern.model.ErrorGenerators;
import de.thatsich.openfx.errorgeneration.intern.model.ErrorState;
import de.thatsich.openfx.errorgeneration.intern.model.Errors;
import de.thatsich.openfx.errorgeneration.intern.view.ErrorDisplayView;
import de.thatsich.openfx.errorgeneration.intern.view.ErrorInputView;
import de.thatsich.openfx.errorgeneration.intern.view.ErrorListView;


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
	protected void bindModel()
	{
		super.bind(IErrors.class).to(Errors.class).in(Scopes.SINGLETON);
		super.bind(IErrorGenerators.class).to(ErrorGenerators.class).in(Scopes.SINGLETON);
		super.bind(IErrorState.class).to(ErrorState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IErrorDisplayView.class).to(ErrorDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IErrorInputView.class).to(ErrorInputView.class).in(Scopes.SINGLETON);
		super.bind(IErrorListView.class).to(ErrorListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindControl()
	{
		super.bind(ErrorInitCommander.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindCommand()
	{

	}

	@Override
	protected void bindService()
	{
		super.bind(ErrorConfigService.class).in(Scopes.SINGLETON);
		super.bind(ErrorFileStorageService.class).in(Scopes.SINGLETON);
	}
}
