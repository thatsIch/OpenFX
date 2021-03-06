package de.thatsich.openfx.preprocessing.api.guice;

import com.google.inject.Scopes;
import de.thatsich.core.guice.AWiringModule;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessingState;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessors;
import de.thatsich.openfx.preprocessing.api.model.ITrainedPreProcessors;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingDisplayView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingInputView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingListView;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.AANNPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.IdentityPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.service.PreProcessingConfigService;
import de.thatsich.openfx.preprocessing.intern.control.command.service.TrainedPreProcessorFileStorageService;
import de.thatsich.openfx.preprocessing.intern.model.PreProcessingState;
import de.thatsich.openfx.preprocessing.intern.model.PreProcessors;
import de.thatsich.openfx.preprocessing.intern.model.TrainedPreProcessors;
import de.thatsich.openfx.preprocessing.intern.view.PreProcessingDisplayView;
import de.thatsich.openfx.preprocessing.intern.view.PreProcessingInputView;
import de.thatsich.openfx.preprocessing.intern.view.PreProcessingListView;


public class PreProcessingWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(PreProcessingWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindModel()
	{
		super.bind(IPreProcessors.class).to(PreProcessors.class).in(Scopes.SINGLETON);
		super.bind(ITrainedPreProcessors.class).to(TrainedPreProcessors.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessingState.class).to(PreProcessingState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IPreProcessingDisplayView.class).to(PreProcessingDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessingInputView.class).to(PreProcessingInputView.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessingListView.class).to(PreProcessingListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindControl()
	{
	}

	@Override
	protected void bindCommand()
	{
		super.bind(AANNPreProcessor.class).in(Scopes.SINGLETON);
		super.bind(IdentityPreProcessor.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService()
	{
		super.bind(PreProcessingConfigService.class).in(Scopes.SINGLETON);
		super.bind(TrainedPreProcessorFileStorageService.class).in(Scopes.SINGLETON);
	}
}
