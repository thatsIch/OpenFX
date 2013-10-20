package de.thatsich.bachelor.imageprocessing.api.core;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageState;
import de.thatsich.bachelor.imageprocessing.restricted.services.ImageConfigService;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageDisplayView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageFileChooser;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageInputView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageListView;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class ImageWiringModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Wires up all the interfaces to their representation 
	 * or implementation.
	 */
	@Override
	protected void configure() {
		super.bind(ImageWiringModule.class).toInstance(this);

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
		super.bind(ImageDisplayView.class).in(Scopes.SINGLETON);
		super.bind(ImageInputView.class).in(Scopes.SINGLETON);
		super.bind(ImageListView.class).in(Scopes.SINGLETON);
		super.bind(ImageFileChooser.class).in(Scopes.SINGLETON);
	}
	
	private void mapServices() {
		super.bind(ImageConfigService.class).in(Scopes.SINGLETON);
	}
	
	private void mapModels() {
		super.bind(ImageEntries.class).in(Scopes.SINGLETON);
		super.bind(ImageState.class).in(Scopes.SINGLETON);
	}
}
