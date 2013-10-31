package de.thatsich.bachelor.prediction.intern.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.bachelor.prediction.api.core.IBinaryPredictions;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.view.BinaryPredictionSplitChannelView;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;

public class PredictionDisplayPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private ImageView nodeImageViewPrediction;
	
	// Injects
	@Inject private IBinaryPredictions binaryPredictions;
	@Inject private BinaryPredictionSplitChannelView binaryPredictionSplitChannelView;

	@Override
	protected void initComponents() {
		this.initImageView();
	}

	@Override
	protected void bindComponents() {
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

	private void initImageViewListener() {
		this.nodeImageViewPrediction.setCursor(Cursor.HAND);
		
		final Stage stage = new Stage();
		final Scene scene = new Scene(binaryPredictionSplitChannelView.getRoot());
		stage.setScene(scene);
		
		this.nodeImageViewPrediction.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.show();
			}
		});

	}
}
