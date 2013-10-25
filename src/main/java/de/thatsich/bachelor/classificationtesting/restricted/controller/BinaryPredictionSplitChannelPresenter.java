package de.thatsich.bachelor.classificationtesting.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;

public class BinaryPredictionSplitChannelPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private HBox nodeHBoxRoot;
	@FXML private ImageView nodeImageViewWithError;
	@FXML private ImageView nodeImageViewErrorIndication;
	@FXML private ImageView nodeImageViewErrorPrediction;
	
	// Injects
	@Inject private BinaryPredictions binaryPredictions;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		this.bindImageView();
		this.initImageView();
	}
	
	private void bindImageView() {
		this.bindImageViewContent();
		this.bindImageViewHandler();
	}
	
	private void bindImageViewContent() {
		this.binaryPredictions.getSelectedBinaryPredictionProperty().addListener(new ChangeListener<BinaryPrediction>() {

			@Override
			public void changed(
					ObservableValue<? extends BinaryPrediction> observable,
					BinaryPrediction oldValue, BinaryPrediction newValue) {

				if (newValue != null) {
					final Image withError = Images.toImage(newValue.getWithErrorProperty().get());
					final Image errorIndication = Images.toImage(newValue.getErrorIndicationProperty().get());
					final Image errorPrediction = Images.toImage(newValue.getErrorPredictionProperty().get());
					nodeImageViewWithError.imageProperty().set(withError);
					nodeImageViewErrorIndication.imageProperty().set(errorIndication);
					nodeImageViewErrorPrediction.imageProperty().set(errorPrediction);
				} else {
					nodeImageViewWithError.imageProperty().set(null);
					nodeImageViewErrorIndication.imageProperty().set(null);
					nodeImageViewErrorPrediction.imageProperty().set(null);
				}
			}
		});
	}

	private void bindImageViewHandler() {
		this.nodeHBoxRoot.setCursor(Cursor.HAND);
		this.nodeHBoxRoot.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				nodeHBoxRoot.getScene().getWindow().hide();
			}
		});
	}
	
	private void initImageView() {
		final BinaryPrediction prediction = this.binaryPredictions.getSelectedBinaryPredictionProperty().get();
		if (prediction != null) {
			final Image withError = Images.toImage(prediction.getWithErrorProperty().get());
			final Image errorIndication = Images.toImage(prediction.getErrorIndicationProperty().get());
			final Image errorPrediction = Images.toImage(prediction.getErrorPredictionProperty().get());
			nodeImageViewWithError.imageProperty().set(withError);
			nodeImageViewErrorIndication.imageProperty().set(errorIndication);
			nodeImageViewErrorPrediction.imageProperty().set(errorPrediction);
		}
	}
}
