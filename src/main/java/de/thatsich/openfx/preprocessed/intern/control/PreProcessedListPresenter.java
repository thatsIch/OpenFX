package de.thatsich.openfx.preprocessed.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.preprocessed.api.model.IPreProcessedVectors;
import de.thatsich.openfx.preprocessed.api.model.IPreProcesseds;
import de.thatsich.openfx.preprocessed.intern.control.command.PreProcessedInitCommander;
import de.thatsich.openfx.preprocessed.intern.control.command.provider.IPreProcessedCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Code representation of PreProcessingListView.
 * Displays the PreProcessingList inside of a TableView
 *
 * @author thatsIch
 */
public class PreProcessedListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private PreProcessedInitCommander init;
	@Inject private IPreProcessedCommandProvider provider;
	@Inject private IPreProcesseds pps;
	@Inject private IPreProcessedVectors vectors;

	// Nodes
	@FXML private TableView<IFeature> nodeTableViewFeatures;
	@FXML private TableColumn<IFeature, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<IFeature, String> nodeTableColumnClassName;
	@FXML private TableColumn<IFeature, String> nodeTableColumnPreProcessorName;
	@FXML private TableColumn<IFeature, Integer> nodeTableColumnTileSize;

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	@Override
	protected void initComponents()
	{

	}

	private void bindTableView()
	{
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}

	private void bindTableViewContent()
	{
		this.nodeTableViewFeatures.itemsProperty().bind(this.pps.list());
		this.log.info("Bound nodeTableViewFeatures to features.");
	}

	private void bindTableViewSelectionModel()
	{
		// change selection > change model
		final TableView.TableViewSelectionModel<IFeature> selectionModel = this.nodeTableViewFeatures.getSelectionModel();

		selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				this.pps.selected().set(newValue);
				this.vectors.list().set(newValue.vectors());

				final int index = selectionModel.getSelectedIndex();
				this.log.info("Seleced index " + index);
			}
			else
			{
				this.pps.selected().set(null);
				this.vectors.list().set(null);
			}
		});
		this.log.info("Bound Model to TableView.");

		// change model > select
		this.pps.selected().addListener((observable, oldValue, newValue) -> selectionModel.select(newValue));
		this.log.info("Bound TableView to Model.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnExtractorName.setCellValueFactory(feature -> feature.getValue().extractorName());
		this.nodeTableColumnClassName.setCellValueFactory(feature -> feature.getValue().className());
		this.nodeTableColumnPreProcessorName.setCellValueFactory(feature -> feature.getValue().preProcessorName());
		this.nodeTableColumnTileSize.setCellValueFactory(feature -> feature.getValue().tileSize().asObject());
		this.log.info("Setup CellValueFactory for nodeTableViewFeatures.");
	}
}
