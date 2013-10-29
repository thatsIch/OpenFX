package de.thatsich.bachelor.errorgeneration.restricted.command.commands;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.opencv.core.Mat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.core.javafx.ACommand;

public class ApplyErrorCommand extends ACommand<ErrorEntry> {

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
	protected ErrorEntry call() throws Exception {
		Mat copy = imageMat.get().clone();
		copy = generator.get().generateError(copy);
		log.info("Error generated.");
		
		final ErrorEntry entry = new ErrorEntry(imageMat.get(), copy, imagePath.get());
		log.info("Instantiated ErrorEntry.");

		return entry;
	}
}
