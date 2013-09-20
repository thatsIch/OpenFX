package de.thatsich.bachelor.javafx;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.core.Log;
import de.thatsich.core.StringErrorGeneratorConverter;
import de.thatsich.core.StringMetricConverter;
import de.thatsich.core.StringPathConverter;
import de.thatsich.core.javafx.ImageFileChooser;
import de.thatsich.core.opencv.IErrorGenerator;
import de.thatsich.core.opencv.IFeatureExtractor;
import de.thatsich.core.opencv.IMetric;

/**
 * Facilitat the communication between
 * View and Application Logic Layer.
 * 
 * Valides the user input and publish corresponding events through EventBus
 * 
 * @author Tran Minh Do
 */
@Singleton
public class DisplayPresenter implements Initializable, IDisplayPresenter {
	
	// GUI Elements
	@FXML private Parent nodeRoot;
	
	@FXML private ChoiceBox<Path> nodeChoiceBoxDisplayImage;
	@FXML private ChoiceBox<IErrorGenerator> nodeChoiceBoxErrorGenerator;
	@FXML private ChoiceBox<IMetric> nodeChoiceBoxMetric;
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	
	@FXML private ImageView nodeImageViewInput;
	@FXML private ImageView nodeImageViewError;
	@FXML private ImageView nodeImageViewMatrix;
	
	@FXML private Slider nodeSliderFrameSize;
	@FXML private Slider nodeSliderThreshold;
	
	@FXML private Button nodeButtonAddImage;
	@FXML private Button nodeButtonRemoveImage;
	@FXML private Button nodeButtonSaveOutput;
	
	@Inject private Log log;
	@Inject private IStateModel stateModel;
	@Inject private ImageFileChooser chooser;
	
	/*
	 * ==================================================
	 * IDisplayPresenter Implementation
	 * ==================================================
	 */
	@Override
	public Parent getRoot() {
		return this.nodeRoot;
	}
	
	/* 
	 * ================================================== 
	 * Initializable Implementation 
	 * ==================================================
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		// ChoiceBoxes
		this.initChoiceBoxDisplayImage();
		this.initChoiceBoxErrorGenerator();
		this.initChoiceBoxMetric();
		this.initChoiceBoxFeatureExtractor();
		
		// ImageViews
		this.initImageViewInput();
		this.initImageModified();
		this.initImageResult();
		
		// Sliders
		this.initSliderFrameSize();
		this.initSliderThreshold();
	}
	
	/**
	 * Bind ChoiceBoxDisplayImage together with its corresponding Model part.
	 */
	private void initChoiceBoxDisplayImage() {
		
		this.nodeChoiceBoxDisplayImage.setConverter(new StringPathConverter());
		this.log.info("Set up StringPathConverter for proper name display.");
		
		this.nodeChoiceBoxDisplayImage.itemsProperty().bindBidirectional(this.stateModel.getImagePathsProperty());
		this.nodeChoiceBoxDisplayImage.valueProperty().bindBidirectional(this.stateModel.getImagePathProperty());
		this.log.info("Bound ChoiceBoxDisplayImage to Model.");
	}
	
	/**
	 * Bind ChoiceBoxErrorGenerator together with its corresponding Model part.
	 */
	private void initChoiceBoxErrorGenerator() {
		
		this.nodeChoiceBoxErrorGenerator.setConverter(new StringErrorGeneratorConverter());
		this.log.info("Set up StringErrorGeneratorConverter for proper name display.");
		
		this.nodeChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.stateModel.getErrorGeneratorsProperty());
		this.nodeChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.stateModel.getErrorGeneratorProperty());
		this.log.info("Bound ChoiceBoxErrorGenerator to Model.");
	}
	
	/**
	 * Bind ChoiceBoxMetric together with its corresponding Model part.
	 */
	private void initChoiceBoxMetric() {
		
		this.nodeChoiceBoxMetric.setConverter(new StringMetricConverter());
		this.log.info("Set up ChoiceBoxMetric for proper name display.");
		
		this.nodeChoiceBoxMetric.itemsProperty().bindBidirectional(this.stateModel.getMetricsProperty());
		this.nodeChoiceBoxMetric.valueProperty().bindBidirectional(this.stateModel.getMetricProperty());
		this.log.info("Bound ChoiceBoxMetric to Model.");
	}
	
	/**
	 * 
	 */
	private void initChoiceBoxFeatureExtractor() {
		
	}
	
	private void initImageViewInput() {

		this.stateModel.getImagePathProperty().addListener(new ChangeListener<Path>() {
			@Override
			public void changed(ObservableValue<? extends Path> observable, Path oldValue, Path newValue) {
				nodeImageViewInput.imageProperty().setValue(new Image("file:" + newValue.toString()));
			}
		});
	}
	
	private void initImageModified() {
		
	}
	
	private void initImageResult() {
		
	}
	
	private void initSliderFrameSize() {
		this.nodeSliderFrameSize.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				log.fine(newValue.toString());
			}
		});
	}
	
	private void initSliderThreshold() {
		this.nodeSliderThreshold.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				log.finer("entering");
				log.fine(newValue.toString());
			}
			
		});		
	}
	
	

	/*
	 * ================================================== 
	 * GUI Implementation 
	 * ==================================================
	 */
	@FXML private void onAddImageAction() throws IOException {

		Path filePath = this.chooser.show();
		if (filePath == null) return;
	
		Path newPath = this.stateModel.getInputPath().resolve(filePath.getFileName());
		this.log.info("Created new Path: " + newPath);
		
		if (Files.exists(newPath)) {
			this.log.info("Duplicate found: File already exists.");
			return;
		}
		
		Path copiedPath = Files.copy(filePath, newPath);
		this.log.info("Copied selection ("+ filePath +") into InputFolder ("+this.stateModel.getInputPath()+"): " + copiedPath);
				
		this.nodeChoiceBoxDisplayImage.getItems().add(copiedPath);
		this.log.info("Added copy to ChoiceBoxDisplayImage: " + copiedPath.toString());
	}
	
	@FXML private void onRemoveImageAction() throws IOException {
		Path choice = this.nodeChoiceBoxDisplayImage.getValue();
		
		if (choice == null || Files.notExists(choice)) {
			this.log.info("Choice was empty. Deleting nothing.");
			return;
		}
		
		this.log.info("Removing Image from List.");
		this.nodeChoiceBoxDisplayImage.getItems().remove(choice);
		
		this.log.info("Deleting Image from InputFolder.");
		Files.delete(choice);
	}
	
	@FXML private void onSaveOutputAction() {

		// Prepare FileNames: use DateTime as unique identifier
		String dateTime = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss").format(new Date());
		this.log.info("TimeStamp: " + dateTime);	
		
		String sOriginal = dateTime + ".original.png";
		String sModified = dateTime + ".modified.png";
		String sResult = dateTime + ".result.png";
		
		Mat mOriginal = Mat.zeros(100, 100, CvType.CV_8SC3);
		Mat mModified = Mat.zeros(100, 100, CvType.CV_8SC3);
		Mat mResult = Mat.zeros(100, 100, CvType.CV_8SC3);
		
		Core.rectangle(mOriginal, new Point(10, 10), new Point(90, 90), new Scalar(255, 0, 0), 2);
		Core.rectangle(mModified, new Point(10, 10), new Point(90, 90), new Scalar(0, 255, 0), 4);
		Core.rectangle(mResult, new Point(10, 10), new Point(90, 90), new Scalar(0, 0, 255), 8);
		
		Highgui.imwrite(sOriginal, mOriginal);
		Highgui.imwrite(sModified, mModified);
		Highgui.imwrite(sResult, mResult);
	}

}
