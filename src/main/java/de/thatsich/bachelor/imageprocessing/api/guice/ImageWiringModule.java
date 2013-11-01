package de.thatsich.bachelor.imageprocessing.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.imageprocessing.api.core.IImageDisplayView;
import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.core.IImageInputView;
import de.thatsich.bachelor.imageprocessing.api.core.IImageListView;
import de.thatsich.bachelor.imageprocessing.api.core.IImageState;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageState;
import de.thatsich.bachelor.imageprocessing.restricted.service.ImageConfigService;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageDisplayView;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageFileChooser;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageInputView;
import de.thatsich.bachelor.imageprocessing.restricted.view.ImageListView;
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
		super.bind(IImageDisplayView.class).to(ImageDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IImageInputView.class).to(ImageInputView.class).in(Scopes.SINGLETON);
		super.bind(IImageListView.class).to(ImageListView.class).in(Scopes.SINGLETON);
		super.bind(ImageFileChooser.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController() {
	}

	@Override
	protected void bindCommand() {
	}

	@Override
	protected void bindModel() {
		super.bind(IImageEntries.class).to(ImageEntries.class).in(Scopes.SINGLETON);
		super.bind(IImageState.class).to(ImageState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService() {
		super.bind(ImageConfigService.class).in(Scopes.SINGLETON);
	}
}
