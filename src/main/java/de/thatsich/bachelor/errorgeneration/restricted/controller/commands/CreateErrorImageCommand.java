package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import org.opencv.core.Mat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.Images;

public class CreateErrorImageCommand extends Command<ErrorEntry> {

	// Properties
	final private ObjectProperty<ErrorEntry> entry = new SimpleObjectProperty<ErrorEntry>();
	
	@Inject
	public CreateErrorImageCommand(@Assisted ErrorEntry entry) {
		this.entry.set(entry);
	}
	
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
}
