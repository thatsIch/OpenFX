package de.thatsich.openfx.taboverview.intern.control;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.openfx.classification.api.view.IClassificationDisplayView;
import de.thatsich.openfx.classification.api.view.IClassificationInputView;
import de.thatsich.openfx.classification.api.view.IClassificationListView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorDisplayView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorInputView;
import de.thatsich.openfx.errorgeneration.api.view.IErrorListView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureDisplayView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureInputView;
import de.thatsich.openfx.featureextraction.api.view.IFeatureListView;
import de.thatsich.openfx.imageprocessing.api.view.IImageDisplayView;
import de.thatsich.openfx.imageprocessing.api.view.IImageInputView;
import de.thatsich.openfx.imageprocessing.api.view.IImageListView;
import de.thatsich.openfx.network.api.view.INetworkDisplayView;
import de.thatsich.openfx.network.api.view.INetworkInputView;
import de.thatsich.openfx.network.api.view.INetworkListView;
import de.thatsich.openfx.prediction.api.view.IPredictionDisplayView;
import de.thatsich.openfx.prediction.api.view.IPredictionInputView;
import de.thatsich.openfx.prediction.api.view.IPredictionListView;
import de.thatsich.openfx.preprocessed.api.view.IPreProcessedDisplayView;
import de.thatsich.openfx.preprocessed.api.view.IPreProcessedInputView;
import de.thatsich.openfx.preprocessed.api.view.IPreProcessedListView;
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
	//	@FXML private BorderPane panePreProcessing;
	//	@FXML private BorderPane panePreProcessed;
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

	@Inject private IPreProcessedInputView preProcessedInputView;
	@Inject private IPreProcessedDisplayView preProcessedDisplayView;
	@Inject private IPreProcessedListView preProcessedListView;

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
		this.paneImageProcessing.setTop(this.imageInputView.getView());
		this.paneImageProcessing.setLeft(this.imageListView.getView());
		this.paneImageProcessing.setCenter(this.imageDisplayView.getView());

		this.paneErrorGeneration.setTop(this.errorInputView.getView());
		this.paneErrorGeneration.setLeft(this.errorListView.getView());
		this.paneErrorGeneration.setCenter(this.errorDisplayView.getView());

		this.paneFeatureExtraction.setTop(this.featureInputView.getView());
		this.paneFeatureExtraction.setLeft(this.featureListView.getView());
		this.paneFeatureExtraction.setCenter(this.featureDisplayView.getView());

		//		this.panePreProcessing.setTop(this.preProcessingInputView.getRoot());
		//		this.panePreProcessing.setLeft(this.preProcessingListView.getRoot());
		//		this.panePreProcessing.setCenter(this.preProcessingDisplayView.getRoot());
		//
		//		this.panePreProcessed.setTop(this.preProcessedInputView.getRoot());
		//		this.panePreProcessed.setLeft(this.preProcessedListView.getRoot());
		//		this.panePreProcessed.setCenter(this.preProcessedDisplayView.getRoot());

		this.paneClassification.setTop(this.classificationInputView.getView());
		this.paneClassification.setLeft(this.classificationListView.getView());
		this.paneClassification.setCenter(this.classificationDisplayView.getView());

		this.panePrediction.setTop(this.predictionInputView.getView());
		this.panePrediction.setLeft(this.predictionListView.getView());
		this.panePrediction.setCenter(this.predictionDisplayView.getView());

		this.paneNetwork.setTop(this.networkInputView.getView());
		this.paneNetwork.setLeft(this.networkListView.getView());
		this.paneNetwork.setCenter(this.networkDisplayView.getView());
	}
}
