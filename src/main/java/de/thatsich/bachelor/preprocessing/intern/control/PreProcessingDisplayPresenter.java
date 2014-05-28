package de.thatsich.bachelor.preprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessings;
import de.thatsich.bachelor.preprocessing.intern.command.PreProcessingInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class PreProcessingDisplayPresenter extends AFXMLPresenter
{
	@Inject PreProcessingInitCommander commander;
	// Nodes
	@FXML Label nodeLabelPreProcessingName;
	@FXML Label nodeLabelInputSize;
	@FXML Label nodeLabelOutputSize;
	@FXML Label nodeLabelID;
	// Injects
	@Inject
	private IPreProcessings preProcessings;

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
	 * Binds all labels to the changing preprocessing
	 */
	private void bindLabels()
	{
		this.preProcessings.getSelectedPreProcessingProperty().addListener(new ChangeListener<IPreProcessing>()
		{
			@Override
			public void changed(ObservableValue<? extends IPreProcessing> observable, IPreProcessing oldValue, IPreProcessing newValue)
			{
				if (newValue != null)
				{
					nodeLabelPreProcessingName.setText(newValue.getPreProcessingNameProperty().getValue());
					nodeLabelInputSize.setText(newValue.getInputSizeProperty().getValue().toString());
					nodeLabelOutputSize.setText(newValue.getOutputSizeProperty().getValue().toString());
					nodeLabelID.setText(newValue.getIdProperty().getValue());
				}
				else
				{
					nodeLabelPreProcessingName.setText(null);
					nodeLabelInputSize.setText(null);
					nodeLabelOutputSize.setText(null);
					nodeLabelID.setText(null);
				}
			}
		});
		this.log.info("Bound labels to changing PreProcessing.");
	}
}
