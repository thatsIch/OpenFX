package de.thatsich.openfx.imageprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.GetLastImageIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.GetLastLocationCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.InitImagesCommand;

import java.nio.file.Path;

public interface IImageInitCommandProvider extends ICommandProvider
{
	public GetLastImageIndexCommand createGetLastImageIndexCommand();

	public GetLastLocationCommand createGetLastLocationCommand();

	public InitImagesCommand createInitImagesCommand(Path imageInputFolderPath);
}
