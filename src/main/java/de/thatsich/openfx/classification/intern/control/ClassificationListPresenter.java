package de.thatsich.openfx.classification.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import de.thatsich.openfx.classification.intern.control.command.ClassificationInitCommander;
import de.thatsich.openfx.classification.intern.control.command.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Code representation of ClassificationListView.
 * Displays the ClassificationList inside of a TableView
 *
 * @author thatsIch
 */
public class ClassificationListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ClassificationInitCommander init;
	@Inject private IClassificationCommandProvider provider;
	@Inject private ITrainedClassifiers binaryClassifications;

	// Nodes
	@FXML private TableView<ITrainedBinaryClassifier> nodeTableViewBinaryClassificationList;
	@FXML private TableColumn<ITrainedBinaryClassifier, String> nodeTableColumnClassifierName;
	@FXML private TableColumn<ITrainedBinaryClassifier, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<ITrainedBinaryClassifier, Integer> nodeTableColumnFrameSize;
	@FXML private TableColumn<ITrainedBinaryClassifier, String> nodeTableErrorName;
	@FXML private TableColumn<ITrainedBinaryClassifier, String> nodeTableColumnID;

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	// ==================================================
	// Initialization Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{

	}

	/**
	 * TableView Bindings
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
		this.nodeTableViewBinaryClassificationList.itemsProperty().bind(this.binaryClassifications.list());
		this.log.info("Bound Content to Model.");
	}

	/**
	 * Bind Selection to Model and vice versa
	 */
	private void bindTableViewSelectionModel()
	{
		this.nodeTableViewBinaryClassificationList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldvalue, newValue) -> {
			this.binaryClassifications.selected().set(newValue);
			this.log.info("Set Selected BinaryClassification in Model.");

			final int index = this.nodeTableViewBinaryClassificationList.getSelectionModel().getSelectedIndex();
			final SetLastBinaryClassificationIndexCommand command = this.provider.createSetLastBinaryClassificationIndexCommand(index);
			command.start();
		});
		this.log.info("Bound Selection to Model.");

		this.binaryClassifications.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewBinaryClassificationList.getSelectionModel().select(newValue));
		this.log.info("Bound Model to Selection.");
	}

	/**
	 * Bind Properties to TableColums
	 */
	private void bindTableViewCellValue()
	{
		this.nodeTableColumnClassifierName.setCellValueFactory(new PropertyValueFactory<>("classificationName"));
		this.nodeTableColumnExtractorName.setCellValueFactory(new PropertyValueFactory<>("extractorName"));
		this.nodeTableColumnFrameSize.setCellValueFactory(new PropertyValueFactory<>("tileSize"));
		this.nodeTableErrorName.setCellValueFactory(new PropertyValueFactory<>("errorName"));
		this.nodeTableColumnID.setCellValueFactory(new PropertyValueFactory<>("id"));
	}
}
