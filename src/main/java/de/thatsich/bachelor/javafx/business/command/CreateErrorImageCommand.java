package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import org.opencv.core.Mat;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.Images;

public class CreateErrorImageCommand extends Command<ErrorEntry> {

	// Properties
	final private ObjectProperty<ErrorEntry> entry = new SimpleObjectProperty<ErrorEntry>();
	
	@Override
	protected Task<ErrorEntry> createTask() {
		return new Task<ErrorEntry>() {

			@Override
			protected ErrorEntry call() throws Exception {
				final Mat imageMat = entry.get().getMergedMat();
				final Path imagePath = entry.get().getPath();
				
				Images.store(imageMat, imagePath);
				log.info("Stored merged Mat into File.");
				
				return entry.get();
			}
		};
	}

	public void setErrorEntry(ErrorEntry entry) { this.entry.set(entry); }
}
