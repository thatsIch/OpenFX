package de.thatsich.bachelor.taboverview.intern.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.classification.api.core.IClassificationDisplayView;
import de.thatsich.bachelor.classification.api.core.IClassificationInputView;
import de.thatsich.bachelor.classification.api.core.IClassificationListView;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorDisplayView;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorInputView;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorListView;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureDisplayView;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureInputView;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureListView;
import de.thatsich.bachelor.imageprocessing.api.core.IImageDisplayView;
import de.thatsich.bachelor.imageprocessing.api.core.IImageInputView;
import de.thatsich.bachelor.imageprocessing.api.core.IImageListView;
import de.thatsich.bachelor.prediction.api.core.IPredictionDisplayView;
import de.thatsich.bachelor.prediction.api.core.IPredictionInputView;
import de.thatsich.bachelor.prediction.api.core.IPredictionListView;


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
	@FXML
	private AnchorPane					paneImageProcessingControl;
	@FXML
	private AnchorPane					paneImageProcessingList;
	@FXML
	private AnchorPane					paneImageProcessingDisplay;

	@FXML
	private AnchorPane					paneErrorGenerationControl;
	@FXML
	private AnchorPane					paneErrorGenerationList;
	@FXML
	private AnchorPane					paneErrorGenerationDisplay;

	@FXML
	private AnchorPane					paneFeatureExtractionControl;
	@FXML
	private AnchorPane					paneFeatureExtractionList;
	@FXML
	private AnchorPane					paneFeatureExtractionDisplay;

	@FXML
	private AnchorPane					panePreProcessingControl;
	@FXML
	private AnchorPane					panePreProcessingList;
	@FXML
	private AnchorPane					panePreProcessingDisplay;

	@FXML
	private AnchorPane					paneClassificationControl;
	@FXML
	private AnchorPane					paneClassificationList;
	@FXML
	private AnchorPane					paneClassificationDisplay;

	@FXML
	private AnchorPane					panePredictionControl;
	@FXML
	private AnchorPane					panePredictionList;
	@FXML
	private AnchorPane					panePredictionDisplay;

	// Views
	@Inject
	private IImageInputView				imageInputView;
	@Inject
	private IImageDisplayView			imageDisplayView;
	@Inject
	private IImageListView				imageListView;

	@Inject
	private IErrorInputView				errorInputView;
	@Inject
	private IErrorDisplayView			errorDisplayView;
	@Inject
	private IErrorListView				errorListView;

	@Inject
	private IFeatureInputView			featureInputView;
	@Inject
	private IFeatureDisplayView			featureDisplayView;
	@Inject
	private IFeatureListView			featureListView;

	@Inject
	private IClassificationInputView	classificationInputView;
	@Inject
	private IClassificationDisplayView	classificationDisplayView;
	@Inject
	private IClassificationListView		classificationListView;

	@Inject
	private IPredictionInputView		predictionInputView;
	@Inject
	private IPredictionDisplayView		predictionDisplayView;
	@Inject
	private IPredictionListView			predictionListView;

	// ==================================================
	// Initializable Implementation
	// ==================================================
	@Override
	public void initialize( URL url, ResourceBundle resourceBundle )
	{
		this.paneImageProcessingControl.getChildren().add( this.imageInputView.getRoot() );
		this.paneImageProcessingList.getChildren().add( this.imageListView.getRoot() );
		this.paneImageProcessingDisplay.getChildren().add( this.imageDisplayView.getRoot() );

		this.paneErrorGenerationControl.getChildren().add( this.errorInputView.getRoot() );
		this.paneErrorGenerationDisplay.getChildren().add( this.errorDisplayView.getRoot() );
		this.paneErrorGenerationList.getChildren().add( this.errorListView.getRoot() );

		this.paneFeatureExtractionControl.getChildren().add( this.featureInputView.getRoot() );
		this.paneFeatureExtractionDisplay.getChildren().add( this.featureDisplayView.getRoot() );
		this.paneFeatureExtractionList.getChildren().add( this.featureListView.getRoot() );

		// this.vBoxPreProcessing.getChildren().add(
		// this.classificationInputView.getRoot() );
		// this.vBoxPreProcessing.getChildren().add(
		// this.classificationDisplayView.getRoot() );
		// this.vBoxPreProcessing.getChildren().add(
		// this.classificationListView.getRoot() );
		//
				
		this.paneClassificationControl.getChildren().add( this.classificationInputView.getRoot() );
		this.paneClassificationDisplay.getChildren().add( this.classificationDisplayView.getRoot() );
		this.paneClassificationList.getChildren().add( this.classificationListView.getRoot() );

		this.panePredictionControl.getChildren().add( this.predictionInputView.getRoot() );
		this.panePredictionDisplay.getChildren().add( this.predictionDisplayView.getRoot() );
		this.panePredictionList.getChildren().add( this.predictionListView.getRoot() );
	}
}
