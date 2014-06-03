package de.thatsich.openfx.preprocessing.api.guice;

import com.google.inject.Scopes;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessingState;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessors;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingDisplayView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingInputView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingListView;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.AANNPreProcessor;
import de.thatsich.openfx.preprocessing.intern.model.PreProcessingState;
import de.thatsich.openfx.preprocessing.intern.model.PreProcessings;
import de.thatsich.openfx.preprocessing.intern.model.PreProcessors;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingConfigService;
import de.thatsich.openfx.preprocessing.intern.view.PreProcessingDisplayView;
import de.thatsich.openfx.preprocessing.intern.view.PreProcessingInputView;
import de.thatsich.openfx.preprocessing.intern.view.PreProcessingListView;
import de.thatsich.core.guice.AWiringModule;


public class PreProcessingWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(PreProcessingWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(PreProcessingConfigService.class).in(Scopes.SINGLETON);

		super.bind(AANNPreProcessor.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IPreProcessingDisplayView.class).to(PreProcessingDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessingInputView.class).to(PreProcessingInputView.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessingListView.class).to(PreProcessingListView.class).in(Scopes.SINGLETON);
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
		super.bind(IPreProcessors.class).to(PreProcessors.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessings.class).to(PreProcessings.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessingState.class).to(PreProcessingState.class).in(Scopes.SINGLETON);
	}
}
