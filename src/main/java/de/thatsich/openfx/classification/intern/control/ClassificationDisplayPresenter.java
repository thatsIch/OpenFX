package de.thatsich.openfx.classification.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import de.thatsich.openfx.classification.intern.control.command.ClassificationInitCommander;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ClassificationDisplayPresenter extends AFXMLPresenter
{

	@Inject ClassificationInitCommander initCommander;
	// Nodes
	@FXML Label nodeLabelClassificationName;
	@FXML Label nodeLabelExtractorName;
	@FXML Label nodeLabelFrameSize;
	@FXML Label nodeLabelErrorName;
	@FXML Label nodeLabelID;
	// Injects
	@Inject
	private IBinaryClassifications binaryClassifications;

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
				nodeLabelClassificationName.setText(newValue.getClassificationNameProperty().getValue());
				nodeLabelExtractorName.setText(newValue.getExtractorNameProperty().getValue());
				nodeLabelFrameSize.setText(newValue.getFrameSizeProperty().getValue().toString());
				nodeLabelErrorName.setText(newValue.getErrorNameProperty().getValue());
				nodeLabelID.setText(newValue.getIdProperty().getValue());
			}
			else
			{
				nodeLabelClassificationName.setText(null);
				nodeLabelExtractorName.setText(null);
				nodeLabelFrameSize.setText(null);
				nodeLabelErrorName.setText(null);
				nodeLabelID.setText(null);
			}
		});
		this.log.info("Bound Labels to changing BinaryClassification.");
	}
}