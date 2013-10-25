package de.thatsich.bachelor.classificationtesting.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.bachelor.classificationtesting.api.entities.BinaryPrediction;
import de.thatsich.bachelor.classificationtesting.restricted.models.state.BinaryPredictions;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;

public class TestDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewPrediction;
	
	// Injects
	@Inject private BinaryPredictions binaryPredictions;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		this.initImageView();
	}

	private void initImageView() {
		this.initImageViewContent();
		this.initImageViewListener();
	}
	
	private void initImageViewContent() {
		this.binaryPredictions.getSelectedBinaryPredictionProperty().addListener(new ChangeListener<BinaryPrediction>() {

			@Override
			public void changed(
					ObservableValue<? extends BinaryPrediction> observable,
					BinaryPrediction oldValue, BinaryPrediction newValue) {
				
				if (newValue != null) {
					final Mat predictionMat = newValue.getErrorPredictionProperty().get();
					final Image image = Images.toImage(predictionMat);
					nodeImageViewPrediction.imageProperty().set(image);
				} else {
					nodeImageViewPrediction.imageProperty().set(null);
				}
				
			}
		});
	}
	
	// TODO mouse handler
	private void initImageViewListener() {
//		this.nodeImageViewPrediction.addEventHandler(EventType<MouseEvent>, new EventHandler<MouseEvent>() {
//
//			@Override
//			public void handle(MouseEvent arg0) {
//			}
//		});
	}
}
