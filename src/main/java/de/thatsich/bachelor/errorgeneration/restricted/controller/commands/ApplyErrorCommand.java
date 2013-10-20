package de.thatsich.bachelor.errorgeneration.restricted.controller.commands;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import org.opencv.core.Mat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IErrorGenerator;

public class ApplyErrorCommand extends Command<ErrorEntry> {

	// Properties
	final private ObjectProperty<Mat> imageMat = new SimpleObjectProperty<Mat>();
	final private ObjectProperty<Path> imagePath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<IErrorGenerator> generator = new SimpleObjectProperty<IErrorGenerator>();
	
	@Inject
	public ApplyErrorCommand(@Assisted Mat imageMat, @Assisted Path imagePath, @Assisted IErrorGenerator generator) {
		this.imageMat.set(imageMat);
		this.imagePath.set(imagePath);
		this.generator.set(generator);
	}
	
	@Override
	protected Task<ErrorEntry> createTask() {
		return new Task<ErrorEntry>() {

			@Override
			protected ErrorEntry call() throws Exception {
				Mat copy = imageMat.get().clone();
				copy = generator.get().generateError(copy);
				log.info("Error generated.");
				
				final ErrorEntry entry = new ErrorEntry(imageMat.get(), copy, imagePath.get());
				log.info("Instantiated ErrorEntry.");

				return entry;
			}
		};
	}
}
