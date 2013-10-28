package de.thatsich.bachelor.imageprocessing.restricted.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.restricted.command.ImageCommandProvider;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.GetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.InitImageEntryListCommand;
import de.thatsich.bachelor.imageprocessing.restricted.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageEntries;
import de.thatsich.bachelor.imageprocessing.restricted.model.ImageState;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class ImageListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML TableView<ImageEntry> nodeTableViewImageList;
	@FXML TableColumn<ImageEntry, String> nodeTableColumnImageList;
	
	// Injects
	@Inject private ImageCommandProvider commander;
	@Inject private ImageEntries imageEntries;
	@Inject private ImageState imageState;

	@Override
	protected void initComponents() {
		this.initTableViewImageEntryList();
	}

	@Override
	protected void bindComponents() {
		this.bindTableView();
	}
	
	// ================================================== 
	// Binding Implementation 
	// ==================================================
	private void bindTableView() {
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}
	
	private void bindTableViewContent() {
		this.nodeTableViewImageList.itemsProperty().bind(this.imageEntries.imageEntriesmageEntryListProperty());
		this.log.info("Bound nodeTableViewImageList to ImageDatabase.");
	}
	
	private void bindTableViewSelectionModel() {
		// change selection > change model
		this.nodeTableViewImageList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override public void changed(ObservableValue<? extends ImageEntry> paramObservableValue, ImageEntry oldValue, ImageEntry newValue) {
				imageEntries.selectedImageEntryProperty().set(newValue);
				
				final int index = nodeTableViewImageList.getSelectionModel().getSelectedIndex();
				final SetLastImageEntryIndexCommand command = commander.createSetLastImageEntryIndexCommand(index);
				command.start();
				log.info("Seleced index " + index);
			}
		});
		this.log.info("Bound Model to TableView.");
		
		// change model > select
		this.imageEntries.selectedImageEntryProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue) {
				nodeTableViewImageList.getSelectionModel().select(newValue);
			}
		});
		this.log.info("Bound TableView to Model.");
	}
	
	private void bindTableViewCellValue() {
		this.nodeTableColumnImageList.setCellValueFactory(new Callback<CellDataFeatures<ImageEntry, String>, ObservableValue<String>>() {
			@Override public ObservableValue<String> call(CellDataFeatures<ImageEntry, String> feature) {
				return new ReadOnlyObjectWrapper<String>(feature.getValue().getName());
			}
		});
		this.log.info("Setup CellValueFactory for nodeTableColumnImageList.");
	}
	
	/**
	 * Initialize all ImageEntries and Preselection of the last selected ImageEntry
	 */
	private void initTableViewImageEntryList() {
		final Path imageInputPath = Paths.get("io/input");
		final EventHandler<WorkerStateEvent> initHandler = new InitImageEntryListSucceededHandler();
		final EventHandler<WorkerStateEvent> lastHandler = new GetLastImageEntryIndexSucceededHandler();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.imageState.getImageInputFolderPathProperty().set(imageInputPath);
		this.log.info("Set ImageInputFolderPath to Model.");
		
		final InitImageEntryListCommand initCommand = this.commander.createInitImageEntryListCommand(imageInputPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized ImageEntryList Retrieval.");
		
		final GetLastImageEntryIndexCommand lastCommand = this.commander.createGetLastImageEntryIndexCommand();
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastImageEntryIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	// ================================================== 
	// Handler Implementation 
	// ==================================================
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing all images entries
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitImageEntryListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<ImageEntry> commandResult = (List<ImageEntry>) event.getSource().getValue();
			log.info("Retrieved ImageEntryList.");
			
			imageEntries.imageEntriesmageEntryListProperty().get().addAll(commandResult);
			log.info("Added ImageEntryList to Model.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for selecting the last selected image entry
	 * 
	 * @author Minh
	 */
	private class GetLastImageEntryIndexSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved last selected image entry index.");
			
			if (commandResult != null && commandResult >= 0 && imageEntries.imageEntriesmageEntryListProperty().size() > commandResult) {
				final ImageEntry selectedImageEntry = imageEntries.imageEntriesmageEntryListProperty().get(commandResult); 
				imageEntries.selectedImageEntryProperty().set(selectedImageEntry);
				log.info("Set last selected image entry index in Model.");
				
				nodeTableViewImageList.getSelectionModel().select(commandResult);
				log.info("Set last selected image entry index in TableView.");
			}
		}
	}
}
