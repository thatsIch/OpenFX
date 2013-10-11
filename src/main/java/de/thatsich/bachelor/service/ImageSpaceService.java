package de.thatsich.bachelor.service;

import java.nio.file.Path;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import de.thatsich.bachelor.javafx.business.command.CopyFileCommand;
import de.thatsich.bachelor.javafx.business.command.DeleteImageEntryCommand;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.ACommandService;

public class ImageSpaceService extends ACommandService {

	public void copyFile(EventHandler<WorkerStateEvent> handler, Path originPath, Path copyPath) {
		CopyFileCommand copyFileCommand = this.commandProvider.get(CopyFileCommand.class);
		copyFileCommand.setOriginPath(originPath);
		copyFileCommand.setCopyPath(copyPath);
		copyFileCommand.setOnSucceeded(handler);
		copyFileCommand.start();
	}
	
	public void deleteImageEntry(EventHandler<WorkerStateEvent> handler, ImageEntry entry) {
		DeleteImageEntryCommand deleteFileCommand = this.commandProvider.get(DeleteImageEntryCommand.class);
		deleteFileCommand.setImageEntry(entry);
		deleteFileCommand.setOnSucceeded(handler);
		deleteFileCommand.start();
	}
}
