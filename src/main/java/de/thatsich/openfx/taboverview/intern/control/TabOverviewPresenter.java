package de.thatsich.openfx.taboverview.intern.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.openfx.classification.api.view.IClassificationDisplayView;
import de.thatsich.openfx.classification.api.view.IClassificationInputView;
import de.thatsich.openfx.classification.api.view.IClassificationListView;
import de.thatsich.openfx.network.api.view.INetworkDisplayView;
import de.thatsich.openfx.network.api.view.INetworkInputView;
import de.thatsich.openfx.network.api.view.INetworkListView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorDisplayView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorInputView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorListView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureDisplayView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureInputView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureListView;
import de.thatsich.openfx.imageprocessing.api.view.IImageDisplayView;
import de.thatsich.openfx.imageprocessing.api.view.IImageInputView;
import de.thatsich.openfx.imageprocessing.api.view.IImageListView;
import de.thatsich.openfx.prediction.api.view.IPredictionDisplayView;
import de.thatsich.openfx.prediction.api.view.IPredictionInputView;
import de.thatsich.openfx.prediction.api.view.IPredictionListView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingDisplayView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingInputView;
import de.thatsich.openfx.preprocessing.api.view.IPreProcessingListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Facilitat the communication between
 * View and Application Logic Layer.
 *
 * Valides the user input and publish corresponding events through EventBus
 *
 * @author Tran Minh Do
 */
@Singleton
public class TabOverviewPresenter implements Initializable
{
	// TabContents
	@FXML private BorderPane paneImageProcessing;
	@FXML private BorderPane paneErrorGeneration;
	@FXML private BorderPane paneFeatureExtraction;
	@FXML private BorderPane panePreProcessing;
	@FXML private BorderPane paneClassification;
	@FXML private BorderPane panePrediction;
	@FXML private BorderPane paneNetwork;

	// Views
	@Inject private IImageInputView imageInputView;
	@Inject private IImageDisplayView imageDisplayView;
	@Inject private IImageListView imageListView;

	@Inject private IErrorInputView errorInputView;
	@Inject private IErrorDisplayView errorDisplayView;
	@Inject private IErrorListView errorListView;

	@Inject private IFeatureInputView featureInputView;
	@Inject private IFeatureDisplayView featureDisplayView;
	@Inject private IFeatureListView featureListView;

	@Inject private IPreProcessingInputView preProcessingInputView;
	@Inject private IPreProcessingDisplayView preProcessingDisplayView;
	@Inject private IPreProcessingListView preProcessingListView;

	@Inject private IClassificationInputView classificationInputView;
	@Inject private IClassificationDisplayView classificationDisplayView;
	@Inject private IClassificationListView classificationListView;

	@Inject private IPredictionInputView predictionInputView;
	@Inject private IPredictionDisplayView predictionDisplayView;
	@Inject private IPredictionListView predictionListView;

	@Inject private INetworkInputView networkInputView;
	@Inject private INetworkDisplayView networkDisplayView;
	@Inject private INetworkListView networkListView;

	// ==================================================
	// Initializable Implementation
	// ==================================================
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle)
	{
		this.paneImageProcessing.setTop(this.imageInputView.getRoot());
		this.paneImageProcessing.setLeft(this.imageListView.getRoot());
		this.paneImageProcessing.setCenter(this.imageDisplayView.getRoot());

		this.paneErrorGeneration.setTop(this.errorInputView.getRoot());
		this.paneErrorGeneration.setLeft(this.errorListView.getRoot());
		this.paneErrorGeneration.setCenter(this.errorDisplayView.getRoot());

		this.paneFeatureExtraction.setTop(this.featureInputView.getRoot());
		this.paneFeatureExtraction.setLeft(this.featureListView.getRoot());
		this.paneFeatureExtraction.setCenter(this.featureDisplayView.getRoot());

		this.panePreProcessing.setTop(this.preProcessingInputView.getRoot());
		this.panePreProcessing.setLeft(this.preProcessingListView.getRoot());
		this.panePreProcessing.setCenter(this.preProcessingDisplayView.getRoot());

		this.paneClassification.setTop(this.classificationInputView.getRoot());
		this.paneClassification.setLeft(this.classificationListView.getRoot());
		this.paneClassification.setCenter(this.classificationDisplayView.getRoot());

		this.panePrediction.setTop(this.predictionInputView.getRoot());
		this.panePrediction.setLeft(this.predictionListView.getRoot());
		this.panePrediction.setCenter(this.predictionDisplayView.getRoot());

		this.paneNetwork.setTop(this.networkInputView.getRoot());
		this.paneNetwork.setLeft(this.networkListView.getRoot());
		this.paneNetwork.setCenter(this.networkDisplayView.getRoot());
	}
}
