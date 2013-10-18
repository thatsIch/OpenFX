package de.thatsich.bachelor.javafx.presentation.d_train;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.IBinaryClassifier;

public class TrainInputPresenter extends AFXMLPresenter {

	// Nodes
	@FXML ChoiceBox<IBinaryClassifier> nodeChoiceBoxBinaryClassifier;

	// ================================================== 
	// Initialization Implementation 
	// ==================================================
	@Override public void initialize(URL location, ResourceBundle resource) {
		
	}

	// ================================================== 
	// Bindings Implementation 
	// ==================================================
	
	// ================================================== 
	// GUI Implementation 
	// ==================================================	
	@FXML private void onTrainBinaryClassifierAction() {
		
	}
}
