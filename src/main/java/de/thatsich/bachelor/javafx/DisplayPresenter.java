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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import org.opencv.core.Mat;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.javafx.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.model.ErrorDatabase.ErrorEntry;
import de.thatsich.bachelor.javafx.model.ImageDatabase;
import de.thatsich.bachelor.javafx.model.ImageDatabase.ImageEntry;
import de.thatsich.core.Log;
import de.thatsich.core.StringErrorGeneratorConverter;
import de.thatsich.core.StringFeatureExtractorConverter;
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
	
	@FXML private ChoiceBox<ImageEntry> nodeChoiceBoxDisplayImage;
	@FXML private ChoiceBox<ErrorEntry> nodeChoiceBoxDisplayedError;
	@FXML private ChoiceBox<IErrorGenerator> nodeChoiceBoxErrorGenerator;
	@FXML private ChoiceBox<IFeatureExtractor> nodeChoiceBoxFeatureExtractor;
	
	@FXML private ImageView nodeImageViewInput;
	@FXML private ImageView nodeImageViewError;
	@FXML private ImageView nodeImageViewMatrix;
	
	@FXML private Slider nodeSliderFrameSize;

	@FXML private TextField nodeTextFieldErrorCount;
	
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
		this.bindChoiceBoxDisplayImage();
		this.bindChoiceBoxDisplayedError();
		this.bindChoiceBoxErrorGenerator();
		this.bindChoiceBoxFeatureExtractor();
		
		// ImageViews
		this.bindImageViewInput();
		this.bindImageModified();
		this.initImageResult();
		
		// TextFields
		this.bindTextFieldErrorCount();
		
		// Sliders
		this.initSliderFrameSize();
	}
	
	/**
	 * Bind ChoiceBoxDisplayImage together with its corresponding Model part.
	 */
	private void bindChoiceBoxDisplayImage() {
		this.nodeChoiceBoxDisplayImage.setConverter(ImageDatabase.ImageEntry.CONVERTER);
		this.log.info("Set up ItemEntryStringConverter for proper name display.");
		
		this.nodeChoiceBoxDisplayImage.itemsProperty().bindBidirectional(this.imageDatabase.getImageEntriesProperty());
		this.nodeChoiceBoxDisplayImage.valueProperty().bindBidirectional(this.imageDatabase.getImageEntryProperty());
		this.log.info("Bound ChoiceBoxDisplayImage to Model.");
	}
	
	private void bindChoiceBoxDisplayedError() {
		this.nodeChoiceBoxDisplayedError.setConverter(ErrorDatabase.ErrorEntry.CONVERTER);
		this.log.info("Set up ErrorEntryStringConverter for proper name display.");
		
		this.nodeChoiceBoxDisplayedError.itemsProperty().bindBidirectional(this.errorDatabase.getErrorEntriesProperty());
		this.nodeChoiceBoxDisplayedError.valueProperty().bindBidirectional(this.errorDatabase.getErrorEntryProperty());
		this.log.info("Bound ChoiceBoxDisplayedError to Model.");
	}
	
	/**
	 * Bind ChoiceBoxErrorGenerator together with its corresponding Model part.
	 */
	private void bindChoiceBoxErrorGenerator() {
		this.nodeChoiceBoxErrorGenerator.setConverter(new StringErrorGeneratorConverter());
		this.log.info("Set up StringErrorGeneratorConverter for proper name display.");
		
		this.nodeChoiceBoxErrorGenerator.itemsProperty().bindBidirectional(this.errorDatabase.getErrorGeneratorsProperty());
		this.nodeChoiceBoxErrorGenerator.valueProperty().bindBidirectional(this.errorDatabase.getErrorGeneratorProperty());
		this.log.info("Bound ChoiceBoxErrorGenerator to Model.");
	}
	
	/**
	 * Bind ChoiceBoxFeatureExtractor to the Model.
	 */
	private void bindChoiceBoxFeatureExtractor() {
		
		this.nodeChoiceBoxFeatureExtractor.setConverter(new StringFeatureExtractorConverter());
		this.log.info("Set up ChoiceBoxFeatureExtractor for proper name display.");
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.stateModel.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.stateModel.getFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
	}
	
	/**
	 * Bind ImageViewInput to the Model and sets the initial Image if there is one loaded.
	 */
	private void bindImageViewInput() {
		this.imageDatabase.getImageEntryProperty().addListener(new ChangeListener<ImageEntry>() {
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue) {
				if (newValue != null) {
					nodeImageViewInput.imageProperty().setValue(newValue.getImage());
				}
			}
		});
		
		ImageEntry entry = this.imageDatabase.getImageEntryProperty().get();
		if (entry != null) {
			this.nodeImageViewInput.imageProperty().setValue(entry.getImage());
		}
	}
	
	/**
	 * Bind TextFieldErrorCount to the Model and tries to validate the input to only non-negative inputs
	 * and initialize the value for it
	 */
	private void bindTextFieldErrorCount() {
		this.nodeTextFieldErrorCount.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d+")) {
					errorDatabase.getErrorLoopCountProperty().set(Integer.parseInt(newValue));
				} else {
					nodeTextFieldErrorCount.setText(oldValue);
				}
			}
		});
		this.errorDatabase.getErrorLoopCountProperty().set(1);
	}
	
	/**
	 * Bind ErrorImageView to the Model and initialize the Image if possible.
	 */
	private void bindImageModified() {
		this.errorDatabase.getErrorEntryProperty().addListener(new ChangeListener<ErrorEntry>() {
			@Override
			public void changed(ObservableValue<? extends ErrorEntry> observable, ErrorEntry oldValue, ErrorEntry newValue) {
				if (newValue != null) {
					nodeImageViewError.imageProperty().setValue(newValue.getImage());
				}
			}
		});
		
		ErrorEntry entry = this.errorDatabase.getErrorEntryProperty().get();
		if (entry != null) {
			this.nodeImageViewError.imageProperty().setValue(entry.getImage());
		}
	}
	
	//TODO bind SliderFrameSize correctly
	private void initSliderFrameSize() {
		this.nodeSliderFrameSize.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				System.out.println(newValue);
			}
		});
	}
	
	private void initImageResult() {
		
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
		ImageEntry imageEntry = this.imageDatabase.getImageEntryProperty().get();
		Mat image = imageEntry.getMat().clone();

		this.errorDatabase.applyErrorOn(image);
	}
	
	@FXML private void onRemoveErrorAction() throws IOException {
		this.errorDatabase.removeSelectedError();
	}
	
	@FXML private void onPermutateErrorsAction() {
		
	}
	
	@FXML private void onResetErrorsAction() throws IOException {
		this.errorDatabase.resetErrorDatabase();
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
