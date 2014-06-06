package de.thatsich.openfx.errorgeneration.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.api.model.IErrors;
import de.thatsich.openfx.errorgeneration.intern.control.command.ErrorInitCommander;
import de.thatsich.openfx.errorgeneration.intern.control.command.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.openfx.errorgeneration.intern.control.provider.IErrorCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ErrorListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ErrorInitCommander init;
	@Inject private IErrors errors;
	@Inject private IErrorCommandProvider provider;

	// Nodes
	@FXML private TableView<IError> nodeTableViewErrorList;
	@FXML private TableColumn<IError, String> nodeTableColumnErrorClass;
	@FXML private TableColumn<IError, String> nodeTableColumnErrorName;

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
		this.nodeTableViewErrorList.itemsProperty().bind(this.errors.list());
		this.log.info("Bound Content to Model.");
	}

	private void bindTableViewSelectionModel()
	{
		this.nodeTableViewErrorList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldvalue, newValue) -> {
			this.errors.selected().set(newValue);

			final int index = this.nodeTableViewErrorList.getSelectionModel().getSelectedIndex();
			final SetLastErrorEntryIndexCommand command = this.provider.createSetLastErrorEntryIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.errors.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewErrorList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	/**
	 * Set up CellFactories for Columns
	 */
	private void bindTableViewCellValue()
	{
		this.nodeTableColumnErrorClass.setCellValueFactory(new PropertyValueFactory<>("clazz"));
		this.nodeTableColumnErrorName.setCellValueFactory(new PropertyValueFactory<>("idProperty"));
	}
}
