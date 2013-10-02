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
import javafx.util.StringConverter;

import org.opencv.core.Mat;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.javafx.model.ErrorDatabase;
import de.thatsich.bachelor.javafx.model.ErrorDatabase.ErrorEntry;
import de.thatsich.bachelor.javafx.model.EvaluationDatabase;
import de.thatsich.bachelor.javafx.model.ImageDatabase;
import de.thatsich.bachelor.javafx.model.ImageDatabase.ImageEntry;
import de.thatsich.core.Log;
import de.thatsich.core.StringErrorGeneratorConverter;
import de.thatsich.core.StringFeatureExtractorConverter;
import de.thatsich.core.javafx.ImageFileChooser;
import de.thatsich.core.opencv.classifier.ABinaryClassifier;
import de.thatsich.core.opencv.classifier.IBinaryClassifier;
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
	@FXML private ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	@FXML private ChoiceBox<ErrorEntry> nodeChoiceBoxSample;
		
	@FXML private ImageView nodeImageViewInput;
	@FXML private ImageView nodeImageViewError;
	@FXML private ImageView nodeImageViewMatrix;
	
	@FXML private Slider nodeSliderFrameSize;

	@FXML private TextField nodeTextFieldErrorCount;
	
	@Inject private Log log;
	@Inject private ImageDatabase imageDatabase;
	@Inject private ErrorDatabase errorDatabase;
	@Inject private EvaluationDatabase evalDatabase;
	@Inject private ImageFileChooser chooser;

	// ================================================== 
	// Initializable Implementation 
	// ==================================================
	@Override public void initialize(URL url, ResourceBundle resourceBundle) {

		// ChoiceBoxes
		this.bindChoiceBoxDisplayImage();
		this.bindChoiceBoxDisplayedError();
		this.bindChoiceBoxErrorGenerator();
		this.bindChoiceBoxFeatureExtractor();
		this.bindChoiceBoxBinaryClassifiers();
		this.bindChoiceBoxSamples();
		
		// ImageViews
		this.bindImageViewInput();
		this.bindImageViewError();
		this.bindImageViewTest();
		
		// TextFields
		this.bindTextFieldErrorCount();
		
		// Sliders
		this.bindSliderFrameSize();
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
		
		this.nodeChoiceBoxFeatureExtractor.itemsProperty().bindBidirectional(this.evalDatabase.getFeatureExtractorsProperty());
		this.nodeChoiceBoxFeatureExtractor.valueProperty().bindBidirectional(this.evalDatabase.getSelectedFeatureExtractorProperty());
		this.log.info("Bound ChoiceBoxFeatureExtractor to Model.");
	}
	
	/**
	 * Bind ChoiceBoxBinaryClassifier to the Model.
	 */
	private void bindChoiceBoxBinaryClassifiers() {
		this.nodeChoiceBoxBinaryClassifier.setConverter(ABinaryClassifier.CONVERTER);
		this.log.info("Set up ChoiceBoxBinaryClassifiers for proper name display.");
		
		this.nodeChoiceBoxBinaryClassifier.itemsProperty().bindBidirectional(this.evalDatabase.getBinaryClassifiersProperty());
		this.nodeChoiceBoxBinaryClassifier.valueProperty().bindBidirectional(this.evalDatabase.getSelectedBinaryClassifierProperty());
		this.log.info("Bound ChoiceBoxBinaryClassifier to Model.");
	}
	
	/**
	 * Bind ChoiceBoxSample to Model
	 * and link both Models
	 */
	private void bindChoiceBoxSamples() {
		this.nodeChoiceBoxSample.setConverter(ErrorDatabase.ErrorEntry.CONVERTER);
		this.log.info("Set up ErrorEntryStringConverter for proper name display.");
		
		this.nodeChoiceBoxSample.itemsProperty().bindBidirectional(this.evalDatabase.getErrorEntriesProperty());
		this.nodeChoiceBoxSample.valueProperty().bindBidirectional(this.evalDatabase.getSelectedErrorEntryProperty());
		this.log.info("Bound ChoiceBoxDisplayedError to Model.");
		
		this.evalDatabase.getErrorEntriesProperty().bind(this.errorDatabase.getErrorEntriesProperty());
		this.log.info("Bound both ErrorEntryLists in Models together.");
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
	private void bindImageViewError() {
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
	
	/**
	 * Set the specific values of the frame size associated with each tick
	 * and sets the labelformatter to fit the representation
	 * 
	 * Java 7 has a bug with the Labels
	 */
	private void bindSliderFrameSize() {
		// write values in power of 2
		// only if slider is let loose
		this.nodeSliderFrameSize.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldBool, Boolean newBool) {
				if (newBool == false) {
					evalDatabase.setFrameSize((int) Math.pow(2, nodeSliderFrameSize.getValue()));
				}				
			}
		});
		this.evalDatabase.setFrameSize((int) Math.pow(2, this.nodeSliderFrameSize.getValue()));
		
		// set labels to pwoer of 2
		this.nodeSliderFrameSize.setLabelFormatter(new StringConverter<Double>() {
			@Override public String toString(Double tick) { return String.format("%d", (int) Math.pow(2, tick)); }
			@Override public Double fromString(String paramString) { return 0.0; }
		});
	}
	
	private void bindImageViewTest() {
		
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
		this.errorDatabase.permutateImageWithErrors(this.imageDatabase.getImageEntries());
	}
	
	@FXML private void onResetErrorsAction() throws IOException {
		this.errorDatabase.resetErrorDatabase();
	}
	
	@FXML private void onTrainAction() {
		this.evalDatabase.trainBinaryClassifier();
	}
	
	@FXML private void onTestAction() {
		this.evalDatabase.testBinaryClassifier();
//		Mat ones = new Mat(3, 3, CvType.CV_8U, new Scalar(100));
//		Mat twos = new Mat(3, 3, CvType.CV_8U, new Scalar(255));
//		Images.show(ones);
//		Images.show(twos);
//		Mat thre = new Mat();
//		Core.absdiff(ones, twos, thre);
//		Images.show(thre);
	}
}
