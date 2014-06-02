package de.thatsich.bachelor.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.intern.control.command.FeatureInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class FeatureDisplayPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private FeatureInitCommander initCommander;
	@Inject private IFeatureVectorSets featureVectors;

	// Nodes
	@FXML private Label nodeLabelClassName;
	@FXML private Label nodeLabelExtractorName;
	@FXML private Label nodeLabelFrameSize;
	@FXML private Label nodeLabelID;
	@FXML private Label nodeLabelFeatureVector;
	@FXML private Label nodeLabelFeatureLabel;

	@Override
	protected void bindComponents()
	{
	}

	// ==================================================
	// Initialization Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{
		this.initLabels();
	}

	/**
	 * Binds all labels to the changing FeatureVector
	 */
	private void initLabels()
	{
		this.nodeLabelFeatureVector.setTooltip(new Tooltip());
		this.log.info("Initialized Tooltip of LabelFeatureVector.");

		this.featureVectors.selectedSet().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				nodeLabelClassName.setText(newValue.className().getValue());
				nodeLabelExtractorName.setText(newValue.extractorName().getValue());
				nodeLabelFrameSize.setText(newValue.frameSize().getValue().toString());
				nodeLabelID.setText(newValue.id().getValue());
			}
			else
			{
				nodeLabelClassName.setText(null);
				nodeLabelExtractorName.setText(null);
				nodeLabelFrameSize.setText(null);
				nodeLabelID.setText(null);
			}
		});
		this.log.info("Bound Labels to changing FeatureVectorSet.");

		this.featureVectors.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				nodeLabelFeatureVector.setText(newValue.vector().getValue().toString());
				nodeLabelFeatureVector.getTooltip().setText(newValue.vector().getValue().toString());
				nodeLabelFeatureLabel.setText(newValue.isPositive().getValue().toString());
			}
			else
			{
				nodeLabelFeatureVector.setText(null);
				nodeLabelFeatureVector.getTooltip().setText(null);
				nodeLabelFeatureLabel.setText(null);
			}
		});
		this.log.info("Bound Labels to chainging FeatureVector.");
	}
}
