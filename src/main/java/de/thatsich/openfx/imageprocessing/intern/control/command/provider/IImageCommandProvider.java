package de.thatsich.openfx.imageprocessing.intern.control.command.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.CopyFileCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.DeleteImageEntryCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastLocationCommand;

import java.nio.file.Path;

public interface IImageCommandProvider extends ICommandProvider
{
	SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(int lastImageEntryIndex);

	CopyFileCommand createCopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);

	DeleteImageEntryCommand createDeleteImageEntryCommand(IImage entry);

	SetLastLocationCommand createSetLastLocationCommand(String lastLocation);
}
