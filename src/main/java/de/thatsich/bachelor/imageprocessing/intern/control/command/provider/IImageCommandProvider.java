package de.thatsich.bachelor.imageprocessing.intern.control.command.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.imageprocessing.api.control.ImageEntry;
import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.CopyFileCommand;
import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.DeleteImageEntryCommand;
import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.intern.control.command.commands.SetLastLocationCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;

public interface IImageCommandProvider extends ICommandProvider
{
	public SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(int lastImageEntryIndex);

	public CopyFileCommand createCopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);

	public DeleteImageEntryCommand createDeleteImageEntryCommand(ImageEntry entry);

	public SetLastLocationCommand createSetLastLocationCommand(String lastLocation);
}
