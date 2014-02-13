package de.thatsich.bachelor.preprocessing.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.preprocessing.api.models.IPreProcessingState;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessings;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessors;
import de.thatsich.bachelor.preprocessing.api.views.IPreProcessingDisplayView;
import de.thatsich.bachelor.preprocessing.api.views.IPreProcessingInputView;
import de.thatsich.bachelor.preprocessing.api.views.IPreProcessingListView;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.AANNPreProcessor;
import de.thatsich.bachelor.preprocessing.intern.model.PreProcessingState;
import de.thatsich.bachelor.preprocessing.intern.model.PreProcessings;
import de.thatsich.bachelor.preprocessing.intern.model.PreProcessors;
import de.thatsich.bachelor.preprocessing.intern.service.PreProcessingConfigService;
import de.thatsich.bachelor.preprocessing.intern.view.PreProcessingDisplayView;
import de.thatsich.bachelor.preprocessing.intern.view.PreProcessingInputView;
import de.thatsich.bachelor.preprocessing.intern.view.PreProcessingListView;
import de.thatsich.core.guice.AWiringModule;


public class PreProcessingWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind( PreProcessingWiringModule.class ).toInstance( this );
	}

	@Override
	protected void bindView()
	{
		super.bind( IPreProcessingDisplayView.class ).to( PreProcessingDisplayView.class ).in( Scopes.SINGLETON );
		super.bind( IPreProcessingInputView.class ).to( PreProcessingInputView.class ).in( Scopes.SINGLETON );
		super.bind( IPreProcessingListView.class ).to( PreProcessingListView.class ).in( Scopes.SINGLETON );
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
		super.bind( IPreProcessors.class ).to( PreProcessors.class ).in( Scopes.SINGLETON );
		super.bind( IPreProcessings.class ).to( PreProcessings.class ).in( Scopes.SINGLETON );
		super.bind( IPreProcessingState.class ).to( PreProcessingState.class ).in( Scopes.SINGLETON );
	}

	@Override
	protected void bindService()
	{
		super.bind( PreProcessingConfigService.class ).in( Scopes.SINGLETON );
		
		super.bind( AANNPreProcessor.class ).in( Scopes.SINGLETON );
	}
}
