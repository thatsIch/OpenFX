package de.thatsich.openfx.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.openfx.featureextraction.intern.control.command.FeatureInitCommander;
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
				this.nodeLabelClassName.setText(newValue.className().getValue());
				this.nodeLabelExtractorName.setText(newValue.extractorName().getValue());
				this.nodeLabelFrameSize.setText(newValue.frameSize().getValue().toString());
				this.nodeLabelID.setText(newValue.id().getValue());
			}
			else
			{
				this.nodeLabelClassName.setText(null);
				this.nodeLabelExtractorName.setText(null);
				this.nodeLabelFrameSize.setText(null);
				this.nodeLabelID.setText(null);
			}
		});
		this.log.info("Bound Labels to changing FeatureVectorSet.");

		this.featureVectors.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				this.nodeLabelFeatureVector.setText(newValue.vector().toString());
				this.nodeLabelFeatureVector.getTooltip().setText(newValue.vector().toString());
				this.nodeLabelFeatureLabel.setText(String.valueOf(newValue.isPositive()));
			}
			else
			{
				this.nodeLabelFeatureVector.setText(null);
				this.nodeLabelFeatureVector.getTooltip().setText(null);
				this.nodeLabelFeatureLabel.setText(null);
			}
		});
		this.log.info("Bound Labels to chainging FeatureVector.");
	}
}
