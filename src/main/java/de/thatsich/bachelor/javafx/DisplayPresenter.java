package de.thatsich.bachelor.javafx;

import java.io.File;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
import de.thatsich.core.javafx.LedMatrix;
import de.thatsich.core.opencv.IErrorGenerator;
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

	private Path lastFileChooserPath;
	private Stage stage;
	
	// GUI Elements
	@FXML private Parent root;
	
	@FXML private ChoiceBox<Path> ChoiceBoxDisplayImage;
	@FXML private ChoiceBox<IErrorGenerator> ChoiceBoxErrorGenerator;
	@FXML private ChoiceBox<IMetric> ChoiceBoxMetric;
	
	@FXML private ImageView ImageViewInput;
	@FXML private ImageView ImageViewError;
	@FXML private LedMatrix LedMatrixDetection;
	
	@FXML private Slider SliderFrameSize;
	@FXML private Slider SliderThreshold;
	
	@FXML private Button ButtonAddImage;
	@FXML private Button ButtonRemoveImage;
	@FXML private Button ButtonSaveOutput;
	
	final private Log log;
//	final private ICommandProvider commandProvider;
	final private IStateModel stateModel;
//	final private EventBus bus;
	
	/**
	 * CTOR
	 * constructor needed, else initialized function will not get activated
	 */
	@Inject
	public DisplayPresenter(IStateModel model, Log log) {
		this.stateModel = model;
		this.log = log;
	}
	

	
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
				
		if (this.stage != null) return this.stage;
		if (this.root == null) throw new IllegalStateException("Root not set.");
		
		final Scene scene = this.root.getScene();
		if (scene == null) throw new IllegalStateException("Scene not set.");
		
		// catch stage
		final Stage stage = (Stage) scene.getWindow();
		if (stage == null) throw new IllegalStateException("Window not set.");
		
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

		this.initChoiceBoxDisplayImage();
		this.initChoiceBoxErrorGenerator();
		this.initChoiceBoxMetric();
		
		// TODO switch to relativ pathes > enables you to put the jar afterswards somewhere else
		this.initImageViewInput();
//		this.initImageModified();
//		this.initImageResult();
		
		this.initSliderFrameSize();
		this.initSliderThreshold();
	}
	
	private void initChoiceBoxDisplayImage() {
		
		// need own converter to display Proper FileNames
		this.ChoiceBoxDisplayImage.setConverter(new StringPathConverter());
		
		this.ChoiceBoxDisplayImage.itemsProperty().bindBidirectional(this.stateModel.getImagePathsProperty());
		this.ChoiceBoxDisplayImage.valueProperty().bindBidirectional(this.stateModel.getImagePathProperty());
	}
	
	private void initChoiceBoxErrorGenerator() {
		
		// own converter for proper name display
		this.ChoiceBoxErrorGenerator.setConverter(new StringErrorGeneratorConverter());
		
		// update each other when either change
		this.ChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.stateModel.getErrorGeneratorsProperty());
		this.ChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.stateModel.getErrorGeneratorProperty());
	}
	
	private void initChoiceBoxMetric() {
		
		// own converter for proper name display
		this.ChoiceBoxMetric.setConverter(new StringMetricConverter());
		
		// update each other when either change
		this.ChoiceBoxMetric.itemsProperty().bindBidirectional(this.stateModel.getMetricsProperty());
		this.ChoiceBoxMetric.valueProperty().bindBidirectional(this.stateModel.getMetricProperty());
	}
	
	private void initImageViewInput() {

		this.stateModel.getImagePathProperty().addListener(new ChangeListener<Path>() {

			@Override
			public void changed(ObservableValue<? extends Path> observable, Path oldValue, Path newValue) {
				System.out.println(new Image(newValue.toString()));
				
//				newValue.
//				test.
				System.out.println(newValue);
//				original.imageProperty().setValue(new Image(newValue.toString()));
			}
			
		});
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
	
	

	/*
	 * ================================================== 
	 * GUI Implementation 
	 * ==================================================
	 */
	@FXML private void onAddImageAction() throws IOException {

		this.log.info("Setting up FileChooser.");	
//		Filechooserb
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter filterPNG = new ExtensionFilter("PNG files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(filterPNG);
		fileChooser.setTitle("Add Image File");
		if (this.lastFileChooserPath != null) {
			fileChooser.setInitialDirectory(this.lastFileChooserPath.toFile());
		}
		else {
			fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		}
		
		File file = fileChooser.showOpenDialog(null);
		this.log.info("Showed FileChooser.");
		
		if (file == null) {
			this.log.warning("No File selected.");
			return;
		}
		
		Path filePath = file.toPath();
		this.log.info("Converted File to Path Object: " + filePath.toString());
		
		this.lastFileChooserPath = filePath.getParent();
		this.log.info("Stored last DirectoryPath: " + this.lastFileChooserPath.toString());
		
		Path newPath = this.stateModel.getInputPath().resolve(filePath.getFileName());
		this.log.info("Created new Path: " + newPath);
		
		if (Files.exists(newPath)) {
			this.log.info("Duplicate found: File already exists.");
			return;
		}
		
		Path copiedPath = Files.copy(filePath, newPath);
		this.log.info("Copied selection ("+ filePath +") into InputFolder ("+this.stateModel.getInputPath()+"): " + copiedPath);
				
		this.ChoiceBoxDisplayImage.getItems().add(copiedPath);
		this.log.info("Added copy to ChoiceBoxDisplayImage: " + copiedPath.toString());
	}
	
	@FXML private void onRemoveImageAction() throws IOException {
		Path choice = this.ChoiceBoxDisplayImage.getValue();
		
		if (choice == null || Files.notExists(choice)) {
			this.log.info("Choice was empty. Deleting nothing.");
			return;
		}
		
		this.log.info("Removing Image from List.");
		this.ChoiceBoxDisplayImage.getItems().remove(choice);
		
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
