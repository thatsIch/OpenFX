package de.thatsich.bachelor.errorgeneration.restricted.command.commands;

import java.nio.file.Path;

import org.opencv.core.Mat;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.core.opencv.Images;

public class CreateErrorImageCommand extends ACommand<ErrorEntry> {

	// Properties
	final private ErrorEntry entry;
	
	@Inject
	public CreateErrorImageCommand(@Assisted ErrorEntry entry) {
		this.entry = entry;
	}

	@Override
	protected ErrorEntry call() throws Exception {
		final Mat imageMat = this.entry.getMergedMat();
		final Path imagePath = this.entry.getPath();
		
		Images.store(imageMat, imagePath);
		log.info("Stored merged Mat into File.");
		
		return this.entry;
	}
}
