package de.thatsich.bachelor.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageState;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageDisplayView;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageInputView;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageListView;
import de.thatsich.bachelor.javafx.business.model.BinaryClassifiers;
import de.thatsich.bachelor.javafx.business.model.ErrorEntries;
import de.thatsich.bachelor.javafx.business.model.ErrorGenerators;
import de.thatsich.bachelor.javafx.business.model.ErrorState;
import de.thatsich.bachelor.javafx.business.model.FeatureExtractors;
import de.thatsich.bachelor.javafx.business.model.FeatureState;
import de.thatsich.bachelor.javafx.business.model.FeatureVectors;
import de.thatsich.bachelor.javafx.presentation.DisplayView;
import de.thatsich.bachelor.javafx.presentation.b_error.ErrorDisplayView;
import de.thatsich.bachelor.javafx.presentation.b_error.ErrorInputView;
import de.thatsich.bachelor.javafx.presentation.b_error.ErrorListView;
import de.thatsich.bachelor.javafx.presentation.c_feature.FeatureDisplayView;
import de.thatsich.bachelor.javafx.presentation.c_feature.FeatureInputView;
import de.thatsich.bachelor.javafx.presentation.c_feature.FeatureListView;
import de.thatsich.bachelor.javafx.presentation.d_train.TrainDisplayView;
import de.thatsich.bachelor.javafx.presentation.d_train.TrainInputView;
import de.thatsich.bachelor.javafx.presentation.d_train.TrainListView;
import de.thatsich.bachelor.javafx.presentation.e_test.TestDisplayView;
import de.thatsich.bachelor.javafx.presentation.e_test.TestInputView;
import de.thatsich.bachelor.javafx.presentation.e_test.TestListView;
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
		
		super.bind(FeatureDisplayView.class).in(Scopes.SINGLETON);
		super.bind(FeatureInputView.class).in(Scopes.SINGLETON);
		super.bind(FeatureListView.class).in(Scopes.SINGLETON);
		
		super.bind(TrainDisplayView.class).in(Scopes.SINGLETON);
		super.bind(TrainInputView.class).in(Scopes.SINGLETON);
		super.bind(TrainListView.class).in(Scopes.SINGLETON);
		
		super.bind(TestDisplayView.class).in(Scopes.SINGLETON);
		super.bind(TestInputView.class).in(Scopes.SINGLETON);
		super.bind(TestListView.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(ConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(ImageState.class).in(Scopes.SINGLETON);
		super.bind(ImageEntries.class).in(Scopes.SINGLETON);
		
		super.bind(ErrorState.class).in(Scopes.SINGLETON);
		super.bind(ErrorEntries.class).in(Scopes.SINGLETON);
		super.bind(ErrorGenerators.class).in(Scopes.SINGLETON);
		
		super.bind(FeatureState.class).in(Scopes.SINGLETON);
		super.bind(FeatureVectors.class).in(Scopes.SINGLETON);
		super.bind(FeatureExtractors.class).in(Scopes.SINGLETON);
		
		super.bind(FeatureState.class).in(Scopes.SINGLETON);
		super.bind(FeatureVectors.class).in(Scopes.SINGLETON);
		super.bind(FeatureExtractors.class).in(Scopes.SINGLETON);
		
		super.bind(BinaryClassifiers.class).in(Scopes.SINGLETON);
	}
}
