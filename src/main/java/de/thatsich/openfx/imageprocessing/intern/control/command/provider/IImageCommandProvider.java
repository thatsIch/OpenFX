package de.thatsich.openfx.imageprocessing.intern.control.command.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.CreateImageCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.DeleteImageCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastImagIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastLocationCommand;

import java.nio.file.Path;

public interface IImageCommandProvider extends ICommandProvider
{
	SetLastImagIndexCommand createSetLastImageIndexCommand(int lastImageEntryIndex);

	CreateImageCommand createCreateImageCommand(Path originPath);

	DeleteImageCommand createDeleteImageCommand(IImage entry);

	SetLastLocationCommand createSetLastLocationCommand(String lastLocation);
}
