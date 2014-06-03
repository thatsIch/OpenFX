package de.thatsich.openfx.preprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;
import de.thatsich.openfx.preprocessing.intern.control.command.PreProcessingInitCommander;
import de.thatsich.openfx.preprocessing.intern.control.command.commands.SetLastPreProcessingIndexCommand;
import de.thatsich.openfx.preprocessing.intern.control.command.provider.IPreProcessingCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	@Inject PreProcessingInitCommander initCommander;
	// Nodes
	@FXML TableView<IPreProcessing> nodeTableViewPreProcessingList;
	@FXML TableColumn<IPreProcessing, String> nodeTableColumnPreProcessingName;
	@FXML TableColumn<IPreProcessing, Integer> nodeTableColumnInputSize;
	@FXML TableColumn<IPreProcessing, Integer> nodeTableColumnOutputSize;
	@FXML TableColumn<IPreProcessing, String> nodeTableColumnID;
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
		this.nodeTableViewPreProcessingList.itemsProperty().bind(this.preProcessings.getPreProcessingListProperty());
		this.log.info("Bound Content to Model.");
	}

	/**
	 * Bind Selection to Model and vice versa
	 */
	private void bindTableViewSelectionModel()
	{
		this.nodeTableViewPreProcessingList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IPreProcessing>()
		{
			@Override
			public void changed(ObservableValue<? extends IPreProcessing> observable, IPreProcessing oldValue, IPreProcessing newValue)
			{
				preProcessings.setSelectedPreProcessing(newValue);
				log.info("Selected " + newValue);

				final int index = nodeTableViewPreProcessingList.getSelectionModel().getSelectedIndex();
				final SetLastPreProcessingIndexCommand command = commander.createSetLastPreProcessingIndexCommand(index);
				command.start();
			}
		});
		this.log.info("Bound Selection to Model.");

		this.preProcessings.getSelectedPreProcessingProperty().addListener(new ChangeListener<IPreProcessing>()
		{
			@Override
			public void changed(ObservableValue<? extends IPreProcessing> observable, IPreProcessing oldValue, IPreProcessing newValue)
			{
				nodeTableViewPreProcessingList.getSelectionModel().select(newValue);
			}
		});
		this.log.info("Bound Model to Selection.");
	}

	/**
	 * Bind Properties to TableColums
	 */
	private void bindTableViewCellValue()
	{
		this.nodeTableColumnPreProcessingName.setCellValueFactory(new PropertyValueFactory<IPreProcessing, String>("getPreProcessingName"));
		this.nodeTableColumnInputSize.setCellValueFactory(new PropertyValueFactory<IPreProcessing, Integer>("getInputSize"));
		this.nodeTableColumnOutputSize.setCellValueFactory(new PropertyValueFactory<IPreProcessing, Integer>("getOutputSize"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<IPreProcessing, String>("getId"));
	}
}
