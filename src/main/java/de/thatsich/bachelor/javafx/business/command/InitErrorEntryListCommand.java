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
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.javafx.Command;

public class InitErrorEntryListCommand extends Command<List<ErrorEntry>> {

	private final ObjectProperty<Path> errorInputFolderPath = new SimpleObjectProperty<Path>();
	
	@Inject
	protected InitErrorEntryListCommand(@Assisted EventHandler<WorkerStateEvent> handler, @Assisted Path errorInputFolderPath) {
		super(handler);
		this.errorInputFolderPath.set(errorInputFolderPath);
	}

	@Override
	protected Task<List<ErrorEntry>> createTask() {
		return new Task<List<ErrorEntry>>() {
			@Override protected List<ErrorEntry> call() throws Exception {
				final Path folderPath = errorInputFolderPath.get();
				final List<ErrorEntry> errorEntryList = new ArrayList<ErrorEntry>();
				
				if (Files.notExists(folderPath) || !Files.isDirectory(folderPath)) Files.createDirectory(folderPath);
				
				final String GLOB_PATTERN = "*.{png,jpeg,jpg,jpe}";
				
				try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath, GLOB_PATTERN)) {
					for (Path child : stream) {
						errorEntryList.add(new ErrorEntry(child));
						log.info("Added " + child + " with Attribute " + Files.probeContentType(child));
					}
				} catch (IOException | DirectoryIteratorException e) {
					e.printStackTrace();
				}
				log.info("All OpenCV Supported Images added: " + errorEntryList.size() + ".");
				
				return errorEntryList;
			}
		};
	}

}
