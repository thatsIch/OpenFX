package de.thatsich.bachelor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.javafx.CommandProvider;
import de.thatsich.bachelor.javafx.DisplayPresenter;
import de.thatsich.bachelor.javafx.DisplayView;
import de.thatsich.bachelor.javafx.ICommandProvider;
import de.thatsich.bachelor.javafx.IDisplayPresenter;
import de.thatsich.bachelor.javafx.IDisplayView;
import de.thatsich.bachelor.javafx.IStateModel;
import de.thatsich.bachelor.javafx.StateModel;
import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.bachelor.service.IConfigService;
import de.thatsich.core.Log;

public class SampleModule extends AbstractModule {

	final private Log log;
	
	public SampleModule() {
		this.log = new Log();
	}
	
	@Override
	protected void configure() {
		super.bind(SampleModule.class).toInstance(this);

		this.mapLogger();
		
		this.mapViews();
		this.mapPresenters();
		this.mapCommands();
		this.mapServices();
		this.mapModels();
	}

	/*
	 * ==================================================
	 * Private Helper
	 * ==================================================
	 * used to map interfaces to implementations
	 */
	private void mapLogger() {
		super.bind(Log.class).toInstance(this.log);
	}
	
	private void mapViews() {
		super.bind(IDisplayView.class).to(DisplayView.class).in(Scopes.SINGLETON);
//		super.bind(ImageFileChooser.class);
	}
	
	private void mapPresenters() {
		super.bind(IDisplayPresenter.class).to(DisplayPresenter.class).in(Scopes.SINGLETON);
	}
	
	private void mapCommands() {
		bind(ICommandProvider.class).to(CommandProvider.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(IConfigService.class).to(ConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		bind(IStateModel.class).to(StateModel.class).in(Scopes.SINGLETON);
	}
}
