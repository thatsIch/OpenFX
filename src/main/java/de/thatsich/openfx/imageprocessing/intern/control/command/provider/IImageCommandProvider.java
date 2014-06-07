package de.thatsich.openfx.imageprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.CreateImageCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.DeleteImageEntryCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastLocationCommand;

import java.nio.file.Path;

public interface IImageCommandProvider extends ICommandProvider
{
	SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(int lastImageEntryIndex);

	CreateImageCommand createCreateImageCommand(Path originPath);

	DeleteImageEntryCommand createDeleteImageEntryCommand(IImage entry);

	SetLastLocationCommand createSetLastLocationCommand(String lastLocation);
}
