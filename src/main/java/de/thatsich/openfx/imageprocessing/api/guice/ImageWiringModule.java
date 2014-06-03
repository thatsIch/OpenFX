package de.thatsich.openfx.imageprocessing.api.guice;

import com.google.inject.Scopes;
import de.thatsich.openfx.imageprocessing.api.view.IImageDisplayView;
import de.thatsich.openfx.imageprocessing.api.model.IImageEntries;
import de.thatsich.openfx.imageprocessing.api.view.IImageInputView;
import de.thatsich.openfx.imageprocessing.api.view.IImageListView;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.openfx.imageprocessing.intern.control.ImageFileChooser;
import de.thatsich.openfx.imageprocessing.intern.model.ImageEntries;
import de.thatsich.openfx.imageprocessing.intern.model.ImageState;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;
import de.thatsich.openfx.imageprocessing.intern.view.ImageDisplayView;
import de.thatsich.openfx.imageprocessing.intern.view.ImageInputView;
import de.thatsich.openfx.imageprocessing.intern.view.ImageListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 *
 * @author Minh
 */
public class ImageWiringModule extends AWiringModule
{

	/*
	 * ==================================================
	 * Private Helper
	 * ==================================================
	 * used to map interfaces to implementations
	 */
	@Override
	protected void bindModule()
	{
		super.bind(ImageWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(ImageConfigService.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IImageDisplayView.class).to(ImageDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IImageInputView.class).to(ImageInputView.class).in(Scopes.SINGLETON);
		super.bind(IImageListView.class).to(ImageListView.class).in(Scopes.SINGLETON);
		super.bind(ImageFileChooser.class).in(Scopes.SINGLETON);
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
		super.bind(IImageEntries.class).to(ImageEntries.class).in(Scopes.SINGLETON);
		super.bind(IImageState.class).to(ImageState.class).in(Scopes.SINGLETON);
	}
}
