package de.thatsich.bachelor.imageprocessing.restricted.services;

import java.nio.file.Path;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.CopyFileCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.DeleteImageEntryCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.GetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.InitImageEntryListCommand;
import de.thatsich.bachelor.imageprocessing.restricted.controller.commands.SetLastImageEntryIndexCommand;

public interface ImageCommandService {
	public InitImageEntryListCommand createInitImageEntryListCommand(Path imageInputFolderPath);
	public SetLastImageEntryIndexCommand createSetLastImageEntryIndexCommand(int lastImageEntryIndex);
	public GetLastImageEntryIndexCommand createGetLastImageEntryIndexCommand();
	public CopyFileCommand createCopyFileCommand(@Assisted("origin") Path originPath, @Assisted("copy") Path copyPath);
	public DeleteImageEntryCommand createDeleteImageEntryCommand(ImageEntry entry);
}
