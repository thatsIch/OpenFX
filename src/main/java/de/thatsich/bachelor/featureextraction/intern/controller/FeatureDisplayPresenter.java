package de.thatsich.bachelor.featureextraction.intern.controller;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.intern.command.FeatureInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

public class FeatureDisplayPresenter extends AFXMLPresenter
{

	// Injects
	@Inject FeatureInitCommander initCommander;
	// Nodes
	@FXML Label nodeLabelClassName;
	@FXML Label nodeLabelExtractorName;
	@FXML Label nodeLabelFrameSize;
	@FXML Label nodeLabelID;
	@FXML Label nodeLabelFeatureVector;
	@FXML Label nodeLabelFeatureLabel;
	@Inject
	private IFeatureVectorSets featureVectors;

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

		this.featureVectors.getSelectedFeatureVectorSetProperty().addListener(new ChangeListener<FeatureVectorSet>()
		{
			@Override
			public void changed(ObservableValue<? extends FeatureVectorSet> observable, FeatureVectorSet oldValue, FeatureVectorSet newValue)
			{
				if (newValue != null)
				{
					nodeLabelClassName.setText(newValue.getClassNameProperty().getValue());
					nodeLabelExtractorName.setText(newValue.getExtractorNameProperty().getValue());
					nodeLabelFrameSize.setText(newValue.getFrameSizeProperty().getValue().toString());
					nodeLabelID.setText(newValue.getIdProperty().getValue());
				}
				else
				{
					nodeLabelClassName.setText(null);
					nodeLabelExtractorName.setText(null);
					nodeLabelFrameSize.setText(null);
					nodeLabelID.setText(null);
				}
			}
		});
		this.log.info("Bound Labels to changing FeatureVectorSet.");

		this.featureVectors.getSelectedFeatureVectorProperty().addListener(new ChangeListener<FeatureVector>()
		{
			@Override
			public void changed(ObservableValue<? extends FeatureVector> observable, FeatureVector oldValue, FeatureVector newValue)
			{
				if (newValue != null)
				{
					nodeLabelFeatureVector.setText(newValue.getVectorProperty().getValue().toString());
					nodeLabelFeatureVector.getTooltip().setText(newValue.getVectorProperty().getValue().toString());
					nodeLabelFeatureLabel.setText(newValue.getIsPositiveProperty().getValue().toString());
				}
				else
				{
					nodeLabelFeatureVector.setText(null);
					nodeLabelFeatureVector.getTooltip().setText(null);
					nodeLabelFeatureLabel.setText(null);
				}
			}
		});
		this.log.info("Bound Labels to chainging FeatureVector.");
	}
}
