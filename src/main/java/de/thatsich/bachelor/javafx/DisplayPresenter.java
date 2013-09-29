package de.thatsich.bachelor.javafx;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.javafx.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.model.ImageDatabase;
import de.thatsich.core.Log;
import de.thatsich.core.StringErrorGeneratorConverter;
import de.thatsich.core.StringFeatureExtractorConverter;
import de.thatsich.core.StringPathConverter;
import de.thatsich.core.javafx.ImageFileChooser;
import de.thatsich.core.opencv.error.IErrorGenerator;
import de.thatsich.core.opencv.extractor.IFeatureExtractor;

/**
 * Facilitat the communication between
 * View and Application Logic Layer.
 * 
 * Valides the user input and publish corresponding events through EventBus
 * 
 * @author Tran Minh Do
 */
@Singleton
public class DisplayPresenter implements Initializable {
	
	// GUI Elements
	@FXML private Parent nodeRoot;
	
	@FXML private ChoiceBox<Path> nodeChoiceBoxDisplayImage;
	@FXML private ChoiceBox<IErrorGenerator> nodeChoiceBoxErrorGenerator;
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	
	@FXML private ImageView nodeImageViewInput;
	@FXML private ImageView nodeImageViewError;
	@FXML private ImageView nodeImageViewMatrix;
	
	@FXML private Slider nodeSliderFrameSize;

	@Inject private Log log;
	@Inject private StateModel stateModel;
	@Inject private ImageDatabase imageDatabase;
	@Inject private ErrorDatabase errorDatabase;
	@Inject private ImageFileChooser chooser;
	
	/*
	 * ==================================================
	 * IDisplayPresenter Implementation
	 * ==================================================
	 */

	
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
		this.initChoiceBoxFeatureExtractor();
		
		// ImageViews
		this.initImageViewInput();
		this.initImageModified();
		this.initImageResult();
		
		// Sliders
		this.initSliderFrameSize();
	}
	
	/**
	 * Bind ChoiceBoxDisplayImage together with its corresponding Model part.
	 */
	private void initChoiceBoxDisplayImage() {
		
		this.nodeChoiceBoxDisplayImage.setConverter(new StringPathConverter());
		this.log.info("Set up StringPathConverter for proper name display.");
		
		this.nodeChoiceBoxDisplayImage.itemsProperty().bindBidirectional(this.imageDatabase.getImagePathsProperty());
		this.nodeChoiceBoxDisplayImage.valueProperty().bindBidirectional(this.imageDatabase.getImagePathProperty());
		this.log.info("Bound ChoiceBoxDisplayImage to Model.");
	}
	
	/**
	 * Bind ChoiceBoxErrorGenerator together with its corresponding Model part.
	 */
	private void initChoiceBoxErrorGenerator() {
		
		this.nodeChoiceBoxErrorGenerator.setConverter(new StringErrorGeneratorConverter());
		this.log.info("Set up StringErrorGeneratorConverter for proper name display.");
		
		this.nodeChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.errorDatabase.getErrorGeneratorsProperty());
		this.nodeChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.errorDatabase.getErrorGeneratorProperty());
		this.log.info("Bound ChoiceBoxErrorGenerator to Model.");
	}
	
	/**
	 * 
	 */
	private void initChoiceBoxFeatureExtractor() {
		
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringFeatureExtractorConverter());
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.stateModel.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.stateModel.getFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
	}
	
	private void initImageViewInput() {

		this.imageDatabase.getImagePathProperty().addListener(new ChangeListener<Path>() {
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
//		this.nodeSliderFrameSize.valueProperty().addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
//				log.fine(newValue.toString());
//			}
//		});
		
		this.nodeSliderFrameSize.valueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				System.out.println(newValue);
			}
		});
	}
	

	// ================================================== 
	// GUI Implementation 
	// ==================================================

	/**
	 * Shows a FileChooser and
	 * adds selected image to model
	 * 
	 * @throws IOException
	 */
	@FXML private void onAddImageAction() throws IOException {

		Path filePath = this.chooser.show();
		if (filePath == null) return;
	
		this.imageDatabase.addImage(filePath);
	}
	
	/**
	 * Removes the currently selected image
	 * 
	 * @throws IOException
	 */
	@FXML private void onRemoveImageAction() throws IOException {
		this.imageDatabase.removeSelectedImage();
	}
	
	/**
	 * Resets the image data base
	 * 
	 * @throws IOException 
	 */
	@FXML private void onResetDatabaseAction() throws IOException {
		this.imageDatabase.resetImageDatabase();
	}
	
	@FXML private void onGenerateErrorsAction() {
		
	}
	
	@FXML private void onRemoveErrorAction() {
		
	}
	
	@FXML private void onPermutateErrorsAction() {
		
	}
	
	@FXML private void onResetErrorsAction() {
		
	}
	
	@FXML private void onTrainAction() {
		
	}
	
	@FXML private void onTestAction() {
		
	}
	
//	@FXML private void onSaveOutputAction() {
//
//		// Prepare FileNames: use DateTime as unique identifier
//		String dateTime = new SimpleDateFormat("yyyy-MM-dd.HH-mm-ss").format(new Date());
//		this.log.info("TimeStamp: " + dateTime);	
//		
//		String sOriginal = dateTime + ".original.png";
//		String sModified = dateTime + ".modified.png";
//		String sResult = dateTime + ".result.png";
//		
//		Mat mOriginal = Mat.zeros(100, 100, CvType.CV_8SC3);
//		Mat mModified = Mat.zeros(100, 100, CvType.CV_8SC3);
//		Mat mResult = Mat.zeros(100, 100, CvType.CV_8SC3);
//		
//		Core.rectangle(mOriginal, new Point(10, 10), new Point(90, 90), new Scalar(255, 0, 0), 2);
//		Core.rectangle(mModified, new Point(10, 10), new Point(90, 90), new Scalar(0, 255, 0), 4);
//		Core.rectangle(mResult, new Point(10, 10), new Point(90, 90), new Scalar(0, 0, 255), 8);
//		
//		Highgui.imwrite(sOriginal, mOriginal);
//		Highgui.imwrite(sModified, mModified);
//		Highgui.imwrite(sResult, mResult);
//	}

//	@FXML private void onTestAction() {
//		Mat lena = Highgui.imread("input/LENA512.BMP");
//		Mat lenaGray = new Mat(lena.width(), lena.height(), CvType.CV_8U);
//		Imgproc.cvtColor(lena, lenaGray, Imgproc.COLOR_BGR2GRAY);
//		
//		Mat histogram = Images.getHistogram(lenaGray);
//		Image img = Images.matToImage(histogram);
//
//		Images.show(img);
//	}
}
