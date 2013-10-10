package de.thatsich.bachelor.service;

import java.nio.file.Path;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import de.thatsich.bachelor.javafx.business.command.CopyFileCommand;
import de.thatsich.bachelor.javafx.business.command.DeleteFileCommand;
import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.ACommandService;

public class FileSystemService extends ACommandService {

	public void copyFile(EventHandler<WorkerStateEvent> handler, Path originPath, Path copyPath) {
		CopyFileCommand copyFileCommand = this.commandProvider.get(CopyFileCommand.class);
		copyFileCommand.setOriginPath(originPath);
		copyFileCommand.setCopyPath(copyPath);
		copyFileCommand.setOnSucceeded(handler);
		copyFileCommand.start();
	}
	
	public void deleteFile(EventHandler<WorkerStateEvent> handler, ImageEntry entry) {
		DeleteFileCommand deleteFileCommand = this.commandProvider.get(DeleteFileCommand.class);
		deleteFileCommand.setEntry(entry);
		deleteFileCommand.setOnSucceeded(handler);
		deleteFileCommand.start();
	}
}
