package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import de.thatsich.core.javafx.Command;

public class DeleteFileCommand extends Command<Void> {

	final private ObjectProperty<Path> filePath = new SimpleObjectProperty<Path>();
	
	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				if (filePath.get() == null) throw new CancellationException("Command not initialized properly.");
				
				Path path = filePath.get();
				if (Files.exists(path)) {
					Files.delete(path);
					log.info("File deleted.");
				}
				
				return null;
			}
		};
	}

	// Setter 
	public void setFilePath(Path filePath) { this.filePath.set(filePath); }
}
