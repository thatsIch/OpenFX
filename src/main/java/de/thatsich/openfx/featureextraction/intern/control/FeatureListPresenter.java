package de.thatsich.openfx.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.api.model.IVectors;
import de.thatsich.openfx.featureextraction.intern.control.command.FeatureInitCommander;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureIndexCommand;
import de.thatsich.openfx.featureextraction.intern.control.command.provider.IFeatureCommandProvider;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FeatureListPresenter extends AFXMLPresenter
{
	@Inject private FeatureInitCommander initCommander;
	@Inject private IFeatures features;
	@Inject private IVectors vectors;
	@Inject private IFeatureCommandProvider provider;

	// Nodes
	@FXML private TableView<IFeature> nodeTableViewFeatures;
	@FXML private TableColumn<IFeature, String> nodeTableColumnExtractorName;
	@FXML private TableColumn<IFeature, String> nodeTableColumnClassName;
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
		this.nodeTableViewFeatures.itemsProperty().bind(this.features.list());
		this.log.info("Bound nodeTableViewFeatures to features.");
	}

	private void bindTableViewSelectionModel()
	{
		// change selection > change model
		final TableView.TableViewSelectionModel<IFeature> selectionModel = this.nodeTableViewFeatures.getSelectionModel();

		selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				this.features.selected().set(newValue);
				this.vectors.get().set(newValue.vectors());

				final int index = selectionModel.getSelectedIndex();
				final SetLastFeatureIndexCommand command = this.provider.createSetLastFeatureIndexCommand(index);
				command.start();
				this.log.info("Seleced index " + index);
			}
			else
			{
				this.features.selected().set(null);
				this.vectors.get().set(null);
			}
		});
		this.log.info("Bound Model to TableView.");

		// change model > select
		this.features.selected().addListener((observable, oldValue, newValue) -> selectionModel.select(newValue));
		this.log.info("Bound TableView to Model.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnExtractorName.setCellValueFactory(feature -> feature.getValue().extractorName());
		this.nodeTableColumnClassName.setCellValueFactory(feature -> feature.getValue().className());
		this.nodeTableColumnTileSize.setCellValueFactory(feature -> feature.getValue().tileSize().asObject());
		this.log.info("Setup CellValueFactory for nodeTableViewFeatures.");
	}
}
