package de.thatsich.bachelor.taboverview.intern.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.thatsich.bachelor.classification.api.views.IClassificationDisplayView;
import de.thatsich.bachelor.classification.api.views.IClassificationInputView;
import de.thatsich.bachelor.classification.api.views.IClassificationListView;
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
import de.thatsich.bachelor.preprocessing.api.views.IPreProcessingDisplayView;
import de.thatsich.bachelor.preprocessing.api.views.IPreProcessingInputView;
import de.thatsich.bachelor.preprocessing.api.views.IPreProcessingListView;
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
	@FXML BorderPane paneImageProcessing;
	@FXML BorderPane paneErrorGeneration;
	@FXML BorderPane paneFeatureExtraction;
	@FXML BorderPane panePreProcessing;
	@FXML BorderPane paneClassification;
	@FXML BorderPane panePrediction;

	// Views
	@Inject IImageInputView imageInputView;
	@Inject IImageDisplayView imageDisplayView;
	@Inject IImageListView imageListView;

	@Inject IErrorInputView errorInputView;
	@Inject IErrorDisplayView errorDisplayView;
	@Inject IErrorListView errorListView;

	@Inject IFeatureInputView featureInputView;
	@Inject IFeatureDisplayView featureDisplayView;
	@Inject IFeatureListView featureListView;

	@Inject IPreProcessingInputView preProcessingInputView;
	@Inject IPreProcessingDisplayView preProcessingDisplayView;
	@Inject IPreProcessingListView preProcessingListView;

	@Inject IClassificationInputView classificationInputView;
	@Inject IClassificationDisplayView classificationDisplayView;
	@Inject IClassificationListView classificationListView;

	@Inject IPredictionInputView predictionInputView;
	@Inject IPredictionDisplayView predictionDisplayView;
	@Inject IPredictionListView predictionListView;

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
	}
}
