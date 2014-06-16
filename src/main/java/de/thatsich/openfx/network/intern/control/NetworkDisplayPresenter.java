package de.thatsich.openfx.network.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.network.api.model.INetworks;
import de.thatsich.openfx.network.intern.control.command.NetworkInitCommander;
import de.thatsich.openfx.network.intern.control.prediction.cnbc.nbc.INBC;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class NetworkDisplayPresenter extends AFXMLPresenter
{

	@Inject private NetworkInitCommander init;
	@Inject private INetworks networks;

	// Nodes
	@FXML private Label nodeLabelNBCCount;
	@FXML private Label nodeLabelAverageBCCount;

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
				final List<INBC> nbcs = newValue.getCnbc().getNbcs();
				int bcCount = 0;
				for (INBC nbc : nbcs)
				{
					bcCount += nbc.getTrainedBinaryClassifier().size();
				}
				final int nbcCount = nbcs.size();
				final double averageBCCount = bcCount / nbcCount;

				this.nodeLabelNBCCount.setText(String.valueOf(nbcCount));
				this.nodeLabelAverageBCCount.setText(String.valueOf(averageBCCount));

				this.log.info("Selected new " + newValue);
			}
			else
			{
				this.nodeLabelNBCCount.setText(null);
				this.nodeLabelAverageBCCount.setText(null);

				this.log.info("Deselected.");
			}
		});
	}
}
