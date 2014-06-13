package de.thatsich.openfx.preprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.preprocessing.api.control.entity.ITrainedPreProcessor;
import de.thatsich.openfx.preprocessing.api.model.ITrainedPreProcessors;
import de.thatsich.openfx.preprocessing.intern.control.command.PreProcessingInitCommander;
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
	private ITrainedPreProcessors preProcessings;

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
		this.preProcessings.selected().addListener(new ChangeListener<ITrainedPreProcessor>()
		{
			@Override
			public void changed(ObservableValue<? extends ITrainedPreProcessor> observable, ITrainedPreProcessor oldValue, ITrainedPreProcessor newValue)
			{
				if (newValue != null)
				{
					nodeLabelPreProcessingName.setText(newValue.nameProperty().getValue());
					nodeLabelInputSize.setText(newValue.inputSizeProperty().getValue().toString());
					nodeLabelOutputSize.setText(newValue.outputSizeProperty().getValue().toString());
					nodeLabelID.setText(newValue.idProperty().getValue());
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
