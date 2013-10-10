package de.thatsich.bachelor.service;

import java.nio.file.Path;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import org.opencv.core.Mat;

import de.thatsich.bachelor.javafx.business.command.ApplyErrorCommand;
import de.thatsich.bachelor.javafx.business.command.CreateErrorImageCommand;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.javafx.ACommandService;
import de.thatsich.core.opencv.IErrorGenerator;

public class ErrorGeneratorService extends ACommandService {

	public void applyErrorImage(EventHandler<WorkerStateEvent> handler, Mat image, Path imagePath, IErrorGenerator generator) {
		ApplyErrorCommand applyErrorCommand = this.commandProvider.get(ApplyErrorCommand.class);
		applyErrorCommand.setImageMat(image);
		applyErrorCommand.setImagePath(imagePath);
		applyErrorCommand.setErrorGenerator(generator);
		applyErrorCommand.setOnSucceeded(handler);
		applyErrorCommand.start();
	}
	
	public void createErrorImage(EventHandler<WorkerStateEvent> handler, ErrorEntry entry) {
		CreateErrorImageCommand createImageCommand = this.commandProvider.get(CreateErrorImageCommand.class);
		createImageCommand.setErrorEntry(entry);
		createImageCommand.setOnSucceeded(handler);
		createImageCommand.start();
	}

//	
//	/**
//	 * Creates for every image in the database
//	 * a set of errors
//	 * 
//	 * @param imageEntries
//	 */
//	public void permutateImageWithErrors(ObservableList<ImageEntry> imageEntries) {
//		for (ImageEntry entry : imageEntries) {
//			for (IErrorGenerator gen : this.errorGenerators.get()) {
//				this.applyErrorOn(entry.getMat(), gen);
//			}
//		}
//	}
}
