package de.thatsich.bachelor.imageprocessing.intern.control.command.provider;

import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.GetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.GetLastLocationCommand;
import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.InitImageEntryListCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IImageInitCommandProvider extends ICommandProvider
{
	public GetLastImageEntryIndexCommand createGetLastImageEntryIndexCommand();

	public GetLastLocationCommand createGetLastLocationCommand();

	public InitImageEntryListCommand createInitImageEntryListCommand(Path imageInputFolderPath);
}
