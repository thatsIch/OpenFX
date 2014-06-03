package de.thatsich.openfx.errorgeneration.intern.control;

import com.google.inject.Inject;
import de.thatsich.openfx.errorgeneration.api.model.IErrorEntries;
import de.thatsich.openfx.errorgeneration.intern.control.command.ErrorInitCommander;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.error.core.ErrorEntry;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ErrorListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ErrorInitCommander init;
	@Inject private IErrorEntries errorEntries;
	@Inject private IErrorCommandProvider provider;

	// Nodes
	@FXML private TableView<ErrorEntry> nodeTableViewErrorList;
	@FXML private TableColumn<ErrorEntry, String> nodeTableColumnErrorClass;
	@FXML private TableColumn<ErrorEntry, String> nodeTableColumnErrorName;

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	// ==================================================
	// Initializable Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{

	}

	/**
	 * Set up ListBinding and SelectionBinding for TableView
	 */
	private void bindTableView()
	{
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}

	private void bindTableViewContent()
	{
		this.nodeTableViewErrorList.itemsProperty().bind(this.errorEntries.errorEntries());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelectionModel()
	{
		this.nodeTableViewErrorList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldvalue, newValue) -> {
			errorEntries.selectedErrorEntry().set(newValue);

			final int index = nodeTableViewErrorList.getSelectionModel().getSelectedIndex();
			final SetLastErrorEntryIndexCommand command = provider.createSetLastErrorEntryIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.errorEntries.selectedErrorEntry().addListener((observable, oldValue, newValue) -> nodeTableViewErrorList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	/**
	 * Set up CellFactories for Columns
	 */
	private void bindTableViewCellValue()
	{
		this.nodeTableColumnErrorClass.setCellValueFactory(new PropertyValueFactory<>("getErrorClass"));
		this.nodeTableColumnErrorName.setCellValueFactory(new PropertyValueFactory<>("getErrorName"));
	}
}
