package de.thatsich.bachelor.prediction.api.guice;

import com.google.inject.Scopes;
import de.thatsich.bachelor.prediction.api.model.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.view.IPredictionDisplayView;
import de.thatsich.bachelor.prediction.api.view.IPredictionInputView;
import de.thatsich.bachelor.prediction.api.view.IPredictionListView;
import de.thatsich.bachelor.prediction.api.model.IPredictionState;
import de.thatsich.bachelor.prediction.intern.model.BinaryPredictions;
import de.thatsich.bachelor.prediction.intern.model.PredictionState;
import de.thatsich.bachelor.prediction.intern.control.command.service.BinaryPredictionConfigService;
import de.thatsich.bachelor.prediction.intern.control.command.service.BinaryPredictionFileStorageService;
import de.thatsich.bachelor.prediction.intern.view.PredictionDisplayView;
import de.thatsich.bachelor.prediction.intern.view.PredictionInputView;
import de.thatsich.bachelor.prediction.intern.view.PredictionListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 *
 * @author Minh
 */
public class PredictionWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(PredictionWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(BinaryPredictionFileStorageService.class).in(Scopes.SINGLETON);
		super.bind(BinaryPredictionConfigService.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IPredictionDisplayView.class).to(PredictionDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IPredictionInputView.class).to(PredictionInputView.class).in(Scopes.SINGLETON);
		super.bind(IPredictionListView.class).to(PredictionListView.class).in(Scopes.SINGLETON);
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
		super.bind(IBinaryPredictions.class).to(BinaryPredictions.class).in(Scopes.SINGLETON);
		super.bind(IPredictionState.class).to(PredictionState.class).in(Scopes.SINGLETON);
	}
}
