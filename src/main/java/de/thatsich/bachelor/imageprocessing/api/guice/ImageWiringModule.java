package de.thatsich.bachelor.imageprocessing.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageState;
import de.thatsich.bachelor.imageprocessing.restricted.services.ImageConfigService;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageDisplayView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageFileChooser;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageInputView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class ImageWiringModule extends AWiringModule {

	/*
	 * ==================================================
	 * Private Helper
	 * ==================================================
	 * used to map interfaces to implementations
	 */
	@Override
	protected void bindModule() {
		super.bind(ImageWiringModule.class).toInstance(this);
	}
	
	@Override
	protected void bindView() {
		super.bind(ImageDisplayView.class).in(Scopes.SINGLETON);
		super.bind(ImageInputView.class).in(Scopes.SINGLETON);
		super.bind(ImageListView.class).in(Scopes.SINGLETON);
		super.bind(ImageFileChooser.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindViewModel() {
	}

	@Override
	protected void bindCommand() {
	}

	@Override
	protected void bindModel() {
		super.bind(ImageEntries.class).in(Scopes.SINGLETON);
		super.bind(ImageState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService() {
		super.bind(ImageConfigService.class).in(Scopes.SINGLETON);
	}
}
