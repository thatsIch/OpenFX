package de.thatsich.bachelor.imageprocessing.restricted.command;

import java.nio.file.Path;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.CopyFileCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.DeleteImageEntryCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IImageCommandProvider extends ICommandProvider {
	public SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(int lastImageEntryIndex);
	public CopyFileCommand createCopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);
	public DeleteImageEntryCommand createDeleteImageEntryCommand(ImageEntry entry);
}
