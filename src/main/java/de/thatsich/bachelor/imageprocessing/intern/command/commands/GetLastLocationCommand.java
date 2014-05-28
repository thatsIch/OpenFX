package de.thatsich.bachelor.imageprocessing.intern.command.commands;

import com.google.inject.Inject;
import de.thatsich.bachelor.imageprocessing.intern.service.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

import java.io.File;
import java.nio.file.Path;

public class GetLastLocationCommand extends ACommand<Path>
{

	@Inject
	private ImageConfigService config;

	@Override
	protected Path call() throws Exception
	{
		final File file = new File(this.config.getLastLocationString());
		return file.toPath();
	}

}
