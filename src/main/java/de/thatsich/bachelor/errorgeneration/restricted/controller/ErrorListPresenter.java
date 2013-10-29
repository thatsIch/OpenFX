package de.thatsich.bachelor.errorgeneration.restricted.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorState;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.command.ErrorCommandProvider;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;

public class ErrorListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML TableView<ErrorEntry> nodeTableViewErrorList;
	@FXML TableColumn<ErrorEntry, String> nodeTableColumnErrorClass;
	@FXML TableColumn<ErrorEntry, String> nodeTableColumnErrorName;
	
	// Injects
	@Inject private IErrorState errorState;
	@Inject private IErrorEntries errorEntryList;
	@Inject private ErrorCommandProvider commander;
	
	// ==================================================
	// Initializable Implementation
	// ==================================================
	@Override
	protected void initComponents() {
		this.initErrorEntryList();
	}

	@Override
	protected void bindComponents() {
		this.bindTableView();
	}
	
	/**
	 * Set up ListBinding and SelectionBinding for TableView
	 */
	private void bindTableView() {
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}
	
	private void bindTableViewContent() {
		this.nodeTableViewErrorList.itemsProperty().bind(this.errorEntryList.getErrorEntryListProperty());
		this.log.info("Bound Content to Model.");
	}
	
	private void bindTableViewSelectionModel() {
		this.nodeTableViewErrorList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ErrorEntry>() {
			@Override public void changed(ObservableValue<? extends ErrorEntry> paramObservableValue, ErrorEntry oldvalue, ErrorEntry newValue) {
				errorEntryList.getSelectedErrorEntryProperty().set(newValue);
				
				final int index = nodeTableViewErrorList.getSelectionModel().getSelectedIndex();
				final SetLastErrorEntryIndexCommand command = commander.createSetLastErrorEntryIndexCommand(index);
				command.start();
			}
		});
		this.log.info("Bound Selection to Model.");
		
		this.errorEntryList.getSelectedErrorEntryProperty().addListener(new ChangeListener<ErrorEntry>() {
			@Override public void changed(ObservableValue<? extends ErrorEntry> observable, ErrorEntry oldValue, ErrorEntry newValue) {
				nodeTableViewErrorList.getSelectionModel().select(newValue);
			}
		});
		this.log.info("Bound Model to Selection.");
	}
	
	/**
	 * Set up CellFactories for Columns
	 */
	private void bindTableViewCellValue() {
		this.nodeTableColumnErrorClass.setCellValueFactory(new PropertyValueFactory<ErrorEntry, String>("getErrorClass"));
		this.nodeTableColumnErrorName.setCellValueFactory(new PropertyValueFactory<ErrorEntry, String>("getErrorName"));
	}
	
	/**
	 * Set up ErrorEntryList and preselects last selected one
	 */
	private void initErrorEntryList() {
		final Path errorInputFolderPath = Paths.get("io/error");
		final InitErrorEntryListSucceededHandler initHandler = new InitErrorEntryListSucceededHandler();
		final GetLastErrorEntryIndexSucceededHandler lastHandler = new GetLastErrorEntryIndexSucceededHandler(); 
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(1);
		
		this.errorState.getErrorEntryFolderPath().set(errorInputFolderPath);
		this.log.info("Set ErrorInputFolderPath to Model.");
		
		final InitErrorEntryListCommand initCommand = this.commander.createInitErrorEntryListCommand(errorInputFolderPath);
		initCommand.setOnSucceeded(initHandler);
		initCommand.setExecutor(executor);
		initCommand.start();
		this.log.info("Initialized ErrorEntryList Retrieval.");
		
		final GetLastErrorEntryIndexCommand lastCommand = this.commander.createGetLastErrorEntryIndexCommand();
		lastCommand.setOnSucceeded(lastHandler);
		lastCommand.setExecutor(executor);
		lastCommand.start();
		this.log.info("Initialized LastErrorEntryIndex Retrieval.");
		
		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
	
	// ================================================== 
	// Handler Implementation 
	// ==================================================	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for initializing the error entry list
	 * 
	 * @author Minh
	 */
	@SuppressWarnings("unchecked")
	private class InitErrorEntryListSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final List<ErrorEntry> entryList = (List<ErrorEntry>) event.getSource().getValue();
			
			errorEntryList.getErrorEntryListProperty().get().addAll(entryList);
			log.info("Initialized all ErrorEntries into Model.");
		}
	}
	
	/**
	 * Handler for what should happen if the Command was successfull 
	 * for getting the last error entry index
	 * 
	 * @author Minh
	 */
	private class GetLastErrorEntryIndexSucceededHandler implements EventHandler<WorkerStateEvent> {
		@Override public void handle(WorkerStateEvent event) {
			final Integer commandResult = (Integer) event.getSource().getValue();
			log.info("Retrieved LastErrorEntryIndex.");
			
			if (commandResult != null && commandResult >= 0 && errorEntryList.getErrorEntryListProperty().size() > commandResult) {
				final ErrorEntry selectedErrorEntry = errorEntryList.getErrorEntryListProperty().get(commandResult);
				errorEntryList.getSelectedErrorEntryProperty().set(selectedErrorEntry);
				log.info("Set last selected error entry index in Model.");
			}
		}
	}
}
