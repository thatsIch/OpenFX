package de.thatsich.openfx.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
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
		this.networks.selected().addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
			{
				// TODO change depending on new value
				this.log.info("Selected new " + newValue);
			}
			else
			{
				this.nodeImageViewNetwork.imageProperty().set(null);

				this.nodeLabelPrecision.setText(null);
				this.nodeLabelRecall.setText(null);
				this.nodeLabelSpecificity.setText(null);
				this.nodeLabelAccuracy.setText(null);

				this.nodeLabelF05.setText(null);
				this.nodeLabelF1.setText(null);
				this.nodeLabelF2.setText(null);

				this.log.info("Deselected.");
			}
		});
	}
}
