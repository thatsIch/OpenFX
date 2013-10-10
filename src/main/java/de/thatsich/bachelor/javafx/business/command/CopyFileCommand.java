package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CancellationException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import de.thatsich.core.javafx.Command;

public class CopyFileCommand extends Command<Path> {
	
	// Properties
	final private ObjectProperty<Path> originPath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<Path> copyPath = new SimpleObjectProperty<Path>();
	
	@Override
	protected Task<Path> createTask() {
		return new Task<Path>() {

			@Override
			protected Path call() throws Exception {
				if (originPath.get() == null || copyPath.get() == null) throw new CancellationException("Command not initialized properly.");
				
				if (Files.exists(copyPath.get())) {
					log.info("Duplicate found: File already exists.");
					this.cancel();
					return null;
				}
				else {
					log.info("Copying File.");
					return Files.copy(originPath.get(), copyPath.get());
				}
			}
		};
	}

	// Setter 
	public void setOriginPath(Path newPath) { this.originPath.set(newPath); }
	public void setCopyPath(Path newPath) { this.copyPath.set(newPath); }
}
