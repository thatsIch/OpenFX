package de.thatsich.openfx.preprocessed.api.guice;

import com.google.inject.Scopes;
import de.thatsich.core.guice.AWiringModule;
import de.thatsich.openfx.preprocessed.api.model.IPreProcessedState;
import de.thatsich.openfx.preprocessed.api.model.IPreProcessedVectors;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;
import de.thatsich.openfx.preprocessed.api.view.IPreProcessedDisplayView;
import de.thatsich.openfx.preprocessed.api.view.IPreProcessedInputView;
import de.thatsich.openfx.preprocessed.api.view.IPreProcessedListView;
import de.thatsich.openfx.preprocessed.intern.control.command.service.PreProcessedFeatureFileStorageService;
import de.thatsich.openfx.preprocessed.intern.model.PreProcessedState;
import de.thatsich.openfx.preprocessed.intern.model.PreProcessedVectors;
import de.thatsich.openfx.preprocessed.intern.model.PreProcesseds;
import de.thatsich.openfx.preprocessed.intern.view.PreProcessedDisplayView;
import de.thatsich.openfx.preprocessed.intern.view.PreProcessedInputView;
import de.thatsich.openfx.preprocessed.intern.view.PreProcessedListView;

/**
 * @author thatsIch
 * @since 13.06.2014.
 */
public class PreProcessedWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(PreProcessedWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindModel()
	{
		super.bind(IPreProcesseds.class).to(PreProcesseds.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessedVectors.class).to(PreProcessedVectors.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessedState.class).to(PreProcessedState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IPreProcessedDisplayView.class).to(PreProcessedDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessedInputView.class).to(PreProcessedInputView.class).in(Scopes.SINGLETON);
		super.bind(IPreProcessedListView.class).to(PreProcessedListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindControl()
	{

	}

	@Override
	protected void bindCommand()
	{

	}

	@Override
	protected void bindService()
	{
		super.bind(PreProcessedFeatureFileStorageService.class).in(Scopes.SINGLETON);
	}
}
