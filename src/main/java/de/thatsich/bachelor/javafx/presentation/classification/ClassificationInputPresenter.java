package de.thatsich.bachelor.javafx.presentation.classification;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.util.StringConverter;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ErrorEntryList;
import de.thatsich.bachelor.javafx.business.model.EvaluationDatabase;
import de.thatsich.bachelor.javafx.business.model.entity.ErrorEntry;
import de.thatsich.core.Log;
import de.thatsich.core.opencv.IBinaryClassifier;

public class ClassificationInputPresenter implements Initializable {
	
	// Nodes
	@FXML private ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;
	@FXML private ChoiceBox<ErrorEntry> nodeChoiceBoxSample;

	// Injects
	@Inject private Log log;
	@Inject private ErrorEntryList errorEntryList;
	@Inject private EvaluationDatabase evalDatabase;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		this.bindChoiceBoxBinaryClassifiers();
		this.bindChoiceBoxSamples();
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	/**
	 * Bind ChoiceBoxBinaryClassifier to the Model.
	 */
	private void bindChoiceBoxBinaryClassifiers() {
		this.nodeChoiceBoxBinaryClassifier.setConverter(new StringConverter<IBinaryClassifier>() {
			@Override public String toString(IBinaryClassifier bc) { return bc.getName(); }
			@Override public IBinaryClassifier fromString(String string) { return null; }
		});
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
		this.nodeChoiceBoxSample.setConverter(new StringConverter<ErrorEntry>() {
			
			@Override public String toString(ErrorEntry entry) {
				return entry.getErrorNameProperty().get();
			}
			
			@Override public ErrorEntry fromString(String paramString) {
				return null;
			}
		});
		this.log.info("Set up ErrorEntryStringConverter for proper name display.");
		
		this.nodeChoiceBoxSample.itemsProperty().bindBidirectional(this.evalDatabase.getErrorEntriesProperty());
		this.nodeChoiceBoxSample.valueProperty().bindBidirectional(this.evalDatabase.getSelectedErrorEntryProperty());
		this.log.info("Bound ChoiceBoxDisplayedError to Model.");
		
		this.evalDatabase.getErrorEntriesProperty().bind(this.errorEntryList.getErrorEntryListProperty());
		this.log.info("Bound both ErrorEntryLists in Models together.");
	}
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================
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
