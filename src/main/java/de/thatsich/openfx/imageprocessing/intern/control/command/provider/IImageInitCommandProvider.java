package de.thatsich.openfx.imageprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.GetLastImageEntryIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.GetLastLocationCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.InitImagesCommand;

import java.nio.file.Path;

public interface IImageInitCommandProvider extends ICommandProvider
{
	public GetLastImageEntryIndexCommand createGetLastImageEntryIndexCommand();

	public GetLastLocationCommand createGetLastLocationCommand();

	public InitImagesCommand createInitImageEntryListCommand(Path imageInputFolderPath);
}
