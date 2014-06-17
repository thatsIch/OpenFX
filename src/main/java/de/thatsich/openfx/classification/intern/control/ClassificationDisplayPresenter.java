package de.thatsich.openfx.classification.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.classification.api.model.ITrainedClassifiers;
import de.thatsich.openfx.classification.intern.control.command.ClassificationInitCommander;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ClassificationDisplayPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ClassificationInitCommander initCommander;
	@Inject private ITrainedClassifiers binaryClassifications;

	// Nodes
	@FXML private Label nodeLabelClassificationName;
	@FXML private Label nodeLabelExtractorName;
	@FXML private Label nodeLabelFrameSize;
	@FXML private Label nodeLabelErrorName;
	@FXML private Label nodeLabelID;

	@Override
	protected void bindComponents()
	{
		this.bindLabels();
	}

	@Override
	protected void initComponents()
	{

	}

	/**
	 * Binds all labels to the changing FeatureVector
	 */
	private void bindLabels()
	{
		this.binaryClassifications.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				this.nodeLabelClassificationName.setText(newValue.classificationName().getValue());
				this.nodeLabelExtractorName.setText(newValue.extractorName().getValue());
				this.nodeLabelFrameSize.setText(newValue.tileSize().getValue().toString());
				this.nodeLabelErrorName.setText(newValue.errorName().getValue());
				this.nodeLabelID.setText(newValue.idProperty().getValue());
			}
			else
			{
				this.nodeLabelClassificationName.setText(null);
				this.nodeLabelExtractorName.setText(null);
				this.nodeLabelFrameSize.setText(null);
				this.nodeLabelErrorName.setText(null);
				this.nodeLabelID.setText(null);
			}
		});
		this.log.info("Bound Labels to changing BinaryClassification.");
	}
}
