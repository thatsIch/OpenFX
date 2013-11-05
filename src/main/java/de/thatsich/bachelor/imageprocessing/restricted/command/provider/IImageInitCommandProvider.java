package de.thatsich.bachelor.imageprocessing.restricted.command.provider;

import java.nio.file.Path;

import de.thatsich.bachelor.imageprocessing.restricted.command.commands.GetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.GetLastLocationCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.InitImageEntryListCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IImageInitCommandProvider extends ICommandProvider {
	public GetLastImageEntryIndexCommand createGetLastImageEntryIndexCommand();
	public GetLastLocationCommand createGetLastLocationCommand(); 
	public InitImageEntryListCommand createInitImageEntryListCommand(Path imageInputFolderPath);
}