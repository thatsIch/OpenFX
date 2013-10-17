package de.thatsich.bachelor.javafx.business.command;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ImageEntry;
import de.thatsich.core.javafx.Command;

public class InitImageEntryListCommand extends Command<List<ImageEntry>> {

	private final ObjectProperty<Path> imageInputPath = new SimpleObjectProperty<Path>();
	
	@Inject
	protected InitImageEntryListCommand(@Assisted Path imageInputPath) {
		this.imageInputPath.set(imageInputPath);
	}

	@Override
	protected Task<List<ImageEntry>> createTask() {
		// TODO Auto-generated method stub
		return new Task<List<ImageEntry>>() {

			@Override
			protected List<ImageEntry> call() throws Exception {
				final Path imageInputFolderPath = imageInputPath.get();
				final List<ImageEntry> result = new ArrayList<ImageEntry>();
				final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";
				
				if (Files.notExists(imageInputFolderPath) || !Files.isDirectory(imageInputFolderPath)) Files.createDirectory(imageInputFolderPath);
				
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(imageInputFolderPath, GLOB_PATTERN)) {
					for (Path child : stream) {
						result.add(new ImageEntry(child.toAbsolutePath()));
						log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
					}
				} catch (IOException | DirectoryIteratorException e) {
					e.printStackTrace();
				}
				log.info("All OpenCV Supported Images added: " + result.size() + ".");
				
				return result;
			}
		};
	}

}
