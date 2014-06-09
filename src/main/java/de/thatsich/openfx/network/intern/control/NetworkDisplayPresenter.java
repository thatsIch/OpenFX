package de.thatsich.openfx.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
import de.thatsich.openfx.network.intern.control.entity.Network;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkDisplayPresenter extends AFXMLPresenter
{
	@Inject private NetworkInitCommander init;
	@Inject private INetworks networks;

	// Nodes
	// TODO change elements
	@FXML private ImageView nodeImageViewNetwork;
	@FXML private Label nodeLabelPrecision;
	@FXML private Label nodeLabelRecall;
	@FXML private Label nodeLabelSpecificity;
	@FXML private Label nodeLabelAccuracy;
	@FXML private Label nodeLabelF05;
	@FXML private Label nodeLabelF1;
	@FXML private Label nodeLabelF2;

	@Override
	protected void bindComponents()
	{

	}

	@Override
	protected void initComponents()
	{
		this.initImageView();
	}

	private void initImageView()
	{
		this.initImageViewContent();
	}

	private void initImageViewContent()
	{
		this.networks.selected().addListener(new ChangeListener<Network>()
		{
			@Override
			public void changed(ObservableValue<? extends Network> observable, Network oldValue, Network newValue)
			{
				if (newValue != null)
				{
					// TODO change depending on new value
					log.info("Selected new " + newValue);
				}
				else
				{
					nodeImageViewNetwork.imageProperty().set(null);

					nodeLabelPrecision.setText(null);
					nodeLabelRecall.setText(null);
					nodeLabelSpecificity.setText(null);
					nodeLabelAccuracy.setText(null);

					nodeLabelF05.setText(null);
					nodeLabelF1.setText(null);
					nodeLabelF2.setText(null);

					log.info("Deselected.");
				}
			}
		});
	}
}
