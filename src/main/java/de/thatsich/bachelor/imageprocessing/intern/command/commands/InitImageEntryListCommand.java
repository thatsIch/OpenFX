package de.thatsich.bachelor.imageprocessing.intern.command.commands;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.core.javafx.ACommand;

public class InitImageEntryListCommand extends ACommand<List<ImageEntry>> {

	private final Path imageInputPath;
	
	@Inject
	protected InitImageEntryListCommand(@Assisted Path imageInputPath) {
		this.imageInputPath = imageInputPath;
	}

	@Override
	protected List<ImageEntry> call() throws Exception {
		final List<ImageEntry> result = new ArrayList<ImageEntry>();
		final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";
		
		if (Files.notExists(this.imageInputPath) || !Files.isDirectory(this.imageInputPath)) Files.createDirectories(this.imageInputPath);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.imageInputPath, GLOB_PATTERN)) {
			for (Path child : stream) {
				final Path imagePath = child.toAbsolutePath();
				final Mat imageMat = Highgui.imread(imagePath.toString(), 0);
				
				result.add(new ImageEntry(imagePath, imageMat));
				this.log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
			}
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
		this.log.info("All OpenCV Supported Images added: " + result.size() + ".");
		
		return result;
	}

}
