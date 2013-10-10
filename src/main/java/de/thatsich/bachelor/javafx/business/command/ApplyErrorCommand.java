package de.thatsich.bachelor.javafx.business.command;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import org.opencv.core.Mat;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.javafx.Command;
import de.thatsich.core.opencv.IErrorGenerator;

public class ApplyErrorCommand extends Command<ErrorEntry> {

	// Properties
	final private ObjectProperty<Mat> imageMat = new SimpleObjectProperty<Mat>();
	final private ObjectProperty<Path> imagePath = new SimpleObjectProperty<Path>();
	final private ObjectProperty<IErrorGenerator> generator = new SimpleObjectProperty<IErrorGenerator>();
	
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

	// Setter
	public void setImageMat(Mat imageMat) { this.imageMat.set(imageMat); }
	public void setImagePath(Path imagePath) { this.imagePath.set(imagePath); }
	public void setErrorGenerator(IErrorGenerator generator) { this.generator.set(generator); }
}
