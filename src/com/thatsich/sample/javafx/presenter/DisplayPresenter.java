package com.thatsich.sample.javafx.presenter;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.thatsich.core.java.Log;
import com.thatsich.sample.javafx.command.ICommandProvider;
import com.thatsich.sample.javafx.view.component.LedMatrix;

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

	private Stage stage;
	
	// GUI Elements
	@FXML private Parent root;
	
	// Original Pane
	@FXML private Button ButtonAddImage;
	@FXML private ChoiceBox<String> ChoiceBoxDisplayImage;
	@FXML private Button ButtonRemoveImage;
	@FXML private ImageView original;
	
	// Modified Pane
	@FXML private ImageView modified;
	@FXML private ChoiceBox<String> ChoiceBoxErrorGenerator;
	
	// Result Pane
	@FXML private LedMatrix result;
	@FXML private Slider SliderFrameSize;
	@FXML private ChoiceBox<String> ChoiceBoxMetric;
	@FXML private Slider SliderThreshold;
	
	// Output
	@FXML private Button ButtonSaveOutput;
	
	private File lastLocation;
	
	
	@Inject
	private Log log;
	
	@Inject
	private ICommandProvider commandProvider;
	
	@SuppressWarnings("unused")
	@Inject
	private EventBus bus;
	
	/**
	 * CTOR
	 * constructor needed, else initialized function will not get activated
	 */
	@Inject
	public DisplayPresenter() {}
	

	
	/*
	 * ==================================================
	 * IDisplayPresenter Implementation
	 * ==================================================
	 */
	@Override
	public Parent getRoot() {
		return this.root;
	}
	
	@Override
	public Stage getStage() {
		
		if (this.stage != null) {
			return this.stage;
		}
		
		// catch root
		if (this.root == null) {
			this.log.severe("Root not set.");
			return null;
		}
		
		// catch scene
		final Scene scene = this.root.getScene();
		if (scene == null) {
			this.log.severe("Scene not set.");
			return null;
		}
		
		// catch stage
		final Stage stage = (Stage) scene.getWindow();
		if (stage == null) {
			this.log.severe("Window not set.");
			return null;
		}
		
		this.stage = stage;
		
		return stage;
	}
	
	/* 
	 * ================================================== 
	 * Initializable Implementation 
	 * ==================================================
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		this.log.info("entering");
		
		this.initSliderFrameSize();
		this.initSliderThreshold();
		
		this.initChoiceBoxDisplayImage();
		this.initChoiceBoxErrorGenerator();
		this.initChoiceBoxMetric();
	}

	/*
	 * ================================================== 
	 * GUI Implementation 
	 * ==================================================
	 */
	@FXML private void onAddImageAction() {
		this.log.info("entering");
		
		// set filter
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter filter = new ExtensionFilter("PNG files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setTitle("Add Image File");
		if (this.lastLocation != null) fileChooser.setInitialDirectory(this.lastLocation);
		
		// get file
		File file = fileChooser.showOpenDialog(null);
		
		if (file == null) {
			this.log.warning("No File selected.");
			return;
		}
		
		File parent = file.getParentFile();
		if (parent.exists() && parent.isDirectory()) {
			this.lastLocation = parent;
		}
		
		// add file to choicebox
		this.ChoiceBoxDisplayImage.getItems().add(file.getName());
	}
	
	@FXML private void onRemoveImageAction() {
		this.log.info("entering");
		
		
	}
	
	@FXML private void onSaveOutputAction() {
		this.log.info("entering");
		
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
	
	private void initSliderFrameSize() {
		this.SliderFrameSize.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
				log.finer("entering");
				log.fine(newValue.toString());
			}
			
		});
	}
	
	private void initSliderThreshold() {
		this.SliderThreshold.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				log.finer("entering");
				log.fine(newValue.toString());
			}
			
		});		
	}
	
	private void initChoiceBoxDisplayImage() {
		this.ChoiceBoxDisplayImage.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				log.finer("entering");
				log.fine(newValue.toString());
			}
		
		});
	}
	
	private void initChoiceBoxErrorGenerator() {
		this.ChoiceBoxErrorGenerator.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				log.finer("entering");
				log.fine(newValue.toString());
			}
		
		});	
	}
	
	private void initChoiceBoxMetric() {
		this.ChoiceBoxMetric.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				log.finer("entering");
				log.fine(newValue.toString());
			}
		
		});	
	}
}
