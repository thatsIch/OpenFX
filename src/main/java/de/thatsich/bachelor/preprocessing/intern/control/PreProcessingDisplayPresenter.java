package de.thatsich.bachelor.preprocessing.intern.control;

import com.google.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import de.thatsich.bachelor.preprocessing.api.entities.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessings;
import de.thatsich.bachelor.preprocessing.intern.command.PreProcessingInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;


public class PreProcessingDisplayPresenter extends AFXMLPresenter
{
	// Nodes
	@FXML private Label					nodeLabelPreProcessingName;
	@FXML private Label					nodeLabelInputSize;
	@FXML private Label					nodeLabelOutputSize;
	@FXML private Label					nodeLabelID;

	// Injects
	@Inject private IPreProcessings		preProcessings;
	@Inject PreProcessingInitCommander	commander;

	@Override
	protected void initComponents()
	{
	}

	@Override
	protected void bindComponents()
	{
		this.bindLabels();
	}

	/**
	 * Binds all labels to the changing preprocessing
	 */
	private void bindLabels()
	{
		this.preProcessings.getSelectedPreProcessingProperty().addListener( new ChangeListener<IPreProcessing>()
		{
			@Override
			public void changed( ObservableValue<? extends IPreProcessing> observable, IPreProcessing oldValue, IPreProcessing newValue )
			{
				if ( newValue != null )
				{
					nodeLabelPreProcessingName.setText( newValue.getPreProcessingNameProperty().getValue().toString() );
					nodeLabelInputSize.setText( newValue.getInputSizeProperty().getValue().toString() );
					nodeLabelOutputSize.setText( newValue.getOutputSizeProperty().getValue().toString() );
					nodeLabelID.setText( newValue.getIdProperty().getValue().toString() );
				}
				else
				{
					nodeLabelPreProcessingName.setText( null );
					nodeLabelInputSize.setText( null );
					nodeLabelOutputSize.setText( null );
					nodeLabelID.setText( null );
				}
			}
		} );
		this.log.info( "Bound labels to changing PreProcessing." );
	}
}
