package de.thatsich.bachelor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.javafx.business.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.business.model.EvaluationDatabase;
import de.thatsich.bachelor.javafx.business.model.FeatureSpace;
import de.thatsich.bachelor.javafx.business.model.ImageDatabase;
import de.thatsich.bachelor.javafx.presentation.DisplayView;
import de.thatsich.bachelor.javafx.presentation.classification.ClassificationDisplayView;
import de.thatsich.bachelor.javafx.presentation.classification.ClassificationInputView;
import de.thatsich.bachelor.javafx.presentation.error.ErrorDisplayView;
import de.thatsich.bachelor.javafx.presentation.error.ErrorInputView;
import de.thatsich.bachelor.javafx.presentation.error.ErrorListView;
import de.thatsich.bachelor.javafx.presentation.image.ImageDisplayView;
import de.thatsich.bachelor.javafx.presentation.image.ImageInputView;
import de.thatsich.bachelor.javafx.presentation.image.ImageListView;
import de.thatsich.bachelor.service.ConfigService;


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
		super.bind(DisplayView.class).in(Scopes.SINGLETON);
		
		super.bind(ImageDisplayView.class).in(Scopes.SINGLETON);
		super.bind(ImageInputView.class).in(Scopes.SINGLETON);
		super.bind(ImageListView.class).in(Scopes.SINGLETON);
		
		super.bind(ErrorDisplayView.class).in(Scopes.SINGLETON);
		super.bind(ErrorInputView.class).in(Scopes.SINGLETON);
		super.bind(ErrorListView.class).in(Scopes.SINGLETON);
		
		super.bind(ClassificationDisplayView.class).in(Scopes.SINGLETON);
		super.bind(ClassificationInputView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(ConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(ImageDatabase.class).in(Scopes.SINGLETON);
		super.bind(ErrorDatabase.class).in(Scopes.SINGLETON);
		super.bind(FeatureSpace.class).in(Scopes.SINGLETON);
		super.bind(EvaluationDatabase.class).in(Scopes.SINGLETON);
	}
}
