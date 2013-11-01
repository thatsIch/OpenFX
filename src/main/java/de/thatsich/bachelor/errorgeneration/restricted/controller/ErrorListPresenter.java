package de.thatsich.bachelor.errorgeneration.restricted.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.command.ErrorInitCommander;
import de.thatsich.bachelor.errorgeneration.restricted.command.IErrorCommandProvider;
import de.thatsich.bachelor.errorgeneration.restricted.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.core.javafx.AFXMLPresenter;

public class ErrorListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML TableView<ErrorEntry> nodeTableViewErrorList;
	@FXML TableColumn<ErrorEntry, String> nodeTableColumnErrorClass;
	@FXML TableColumn<ErrorEntry, String> nodeTableColumnErrorName;
	
	// Injects
	@Inject private IErrorEntries errorEntryList;
	@Inject private IErrorCommandProvider commander;
	
	@Inject ErrorInitCommander initCommander;
	
	// ==================================================
	// Initializable Implementation
	// ==================================================
	@Override
	protected void initComponents() {
		
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
}
