package de.thatsich.openfx.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.api.model.IVectors;
import de.thatsich.openfx.featureextraction.intern.control.command.FeatureInitCommander;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class FeatureDisplayPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private FeatureInitCommander init;
	@Inject private IFeatures features;
	@Inject private IVectors vectors;

	// Nodes
	@FXML private TableView<IFeatureVector> nodeTableViewVectors;
	@FXML private TableColumn<IFeatureVector, String> nodeTableColumnVector;
	@FXML private TableColumn<IFeatureVector, Boolean> nodeTableColumnLabel;

	@FXML private Label nodeLabelCount;
	@FXML private Label nodeLabelDimension;

	@Override
	protected void bindComponents()
	{
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();

		this.bindLabelContent();
	}

	private void bindTableViewContent()
	{
		this.nodeTableViewVectors.itemsProperty().bind(this.vectors.get());
		this.log.info("Bound nodeTableViewVectors to vectors.");
	}

	private void bindTableViewSelectionModel()
	{
		final TableView.TableViewSelectionModel<IFeatureVector> selectionModel = this.nodeTableViewVectors.getSelectionModel();

		this.vectors.selected().bind(selectionModel.selectedItemProperty());
		this.log.info("Bound Model to TableView.");

		// change model > select
		this.vectors.selected().addListener((observable, oldValue, newValue) -> selectionModel.select(newValue));
		this.log.info("Bound TableView to Model.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnVector.setCellValueFactory(feature -> new ReadOnlyStringWrapper(feature.getValue().vector().get().toString()));
		this.nodeTableColumnLabel.setCellValueFactory(feature -> feature.getValue().isPositive());
		this.log.info("Setup CellValueFactory for nodeTableColumnList.");
	}

	private void bindLabelContent()
	{
		this.features.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				final List<IFeatureVector> vectors = newValue.vectors();

				this.nodeLabelCount.setText(String.valueOf(vectors.size()));
				this.nodeLabelDimension.setText(String.valueOf(vectors.get(0).vector().size()));
			}
			else
			{
				this.nodeLabelCount.setText(null);
				this.nodeLabelDimension.setText(null);
			}
		});
		this.log.info("Bound Labels to changing FeatureVectorSet.");
	}

	@Override
	protected void initComponents()
	{

	}
}
