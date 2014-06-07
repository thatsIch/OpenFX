package de.thatsich.openfx.preprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;
import de.thatsich.openfx.preprocessing.intern.control.command.PreProcessingInitCommander;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Code representation of PreProcessingListView.
 * Displays the PreProcessingList inside of a TableView
 *
 * @author thatsIch
 */
public class PreProcessingListPresenter extends AFXMLPresenter
{
	@Inject private PreProcessingInitCommander initCommander;
	// Nodes
	@FXML private TableView<IPreProcessing> nodeTableViewPreProcessingList;
	@FXML private TableColumn<IPreProcessing, String> nodeTableColumnPreProcessingName;
	@FXML private TableColumn<IPreProcessing, Integer> nodeTableColumnInputSize;
	@FXML private TableColumn<IPreProcessing, Integer> nodeTableColumnOutputSize;
	@FXML private TableColumn<IPreProcessing, String> nodeTableColumnID;
	// Injects
	@Inject
	private IPreProcessingCommandProvider commander;
	@Inject
	private IPreProcessings preProcessings;

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	@Override
	protected void initComponents()
	{

	}

	/**
	 * Tableview Binding
	 */
	private void bindTableView()
	{
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}

	/**
	 * Bind Content to TableView
	 */
	private void bindTableViewContent()
	{
		this.nodeTableViewPreProcessingList.itemsProperty().bind(this.preProcessings.list());
		this.log.info("Bound Content to Model.");
	}

	/**
	 * Bind Selection to Model and vice versa
	 */
	private void bindTableViewSelectionModel()
	{
		this.nodeTableViewPreProcessingList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			this.preProcessings.selected().set(newValue);
			this.log.info("Selected " + newValue);

			final int index = this.nodeTableViewPreProcessingList.getSelectionModel().getSelectedIndex();
			final SetLastPreProcessingIndexCommand command = this.commander.createSetLastPreProcessingIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.preProcessings.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewPreProcessingList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	/**
	 * Bind Properties to TableColums
	 */
	private void bindTableViewCellValue()
	{
		this.nodeTableColumnPreProcessingName.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.nodeTableColumnInputSize.setCellValueFactory(new PropertyValueFactory<>("inputSize"));
		this.nodeTableColumnOutputSize.setCellValueFactory(new PropertyValueFactory<>("outputSize"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
	}
}
