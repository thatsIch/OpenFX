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


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class WiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(WiringModule.class).toInstance(this);

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
	private void mapViews() {
		super.bind(IDisplayView.class).to(DisplayView.class).in(Scopes.SINGLETON);
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
