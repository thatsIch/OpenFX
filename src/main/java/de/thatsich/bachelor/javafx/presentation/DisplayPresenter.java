package de.thatsich.bachelor.javafx.presentation;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.javafx.presentation.classification.ClassificationDisplayView;
import de.thatsich.bachelor.javafx.presentation.classification.ClassificationInputView;
import de.thatsich.bachelor.javafx.presentation.error.ErrorDisplayView;
import de.thatsich.bachelor.javafx.presentation.error.ErrorInputView;
import de.thatsich.bachelor.javafx.presentation.image.ImageDisplayView;
import de.thatsich.bachelor.javafx.presentation.image.ImageInputView;

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
	
	// Nodes
	@FXML private GridPane nodeRoot;

	// Views
	@Inject private ImageInputView imageInputView;
	@Inject private ImageDisplayView imageDisplayView;
	@Inject private ErrorInputView errorInputView;
	@Inject private ErrorDisplayView errorDisplayView;
	@Inject private ClassificationDisplayView classificationDisplayView;
	@Inject private ClassificationInputView classificationInputView;
	
	// ================================================== 
	// Initializable Implementation 
	// ==================================================
	@Override public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.nodeRoot.add(this.imageInputView.getRoot(), 0, 1);
		GridPane.setValignment(this.imageInputView.getRoot(), VPos.TOP);
		GridPane.setHalignment(this.imageInputView.getRoot(), HPos.CENTER);
		this.nodeRoot.add(this.imageDisplayView.getRoot(), 0, 2);
		GridPane.setValignment(this.imageDisplayView.getRoot(), VPos.TOP);
		GridPane.setHalignment(this.imageDisplayView.getRoot(), HPos.CENTER);
		
		this.nodeRoot.add(this.errorInputView.getRoot(), 1, 1);
		GridPane.setValignment(this.errorInputView.getRoot(), VPos.TOP);
		GridPane.setHalignment(this.errorInputView.getRoot(), HPos.CENTER);
		this.nodeRoot.add(this.errorDisplayView.getRoot(), 1, 2);
		GridPane.setValignment(this.errorDisplayView.getRoot(), VPos.TOP);
		GridPane.setHalignment(this.errorDisplayView.getRoot(), HPos.CENTER);
//		
		this.nodeRoot.add(this.classificationInputView.getRoot(), 2, 1);
		GridPane.setValignment(this.classificationInputView.getRoot(), VPos.TOP);
		GridPane.setHalignment(this.classificationInputView.getRoot(), HPos.CENTER);
		this.nodeRoot.add(this.classificationDisplayView.getRoot(), 2, 2);
		GridPane.setValignment(this.classificationDisplayView.getRoot(), VPos.TOP);
		GridPane.setHalignment(this.classificationDisplayView.getRoot(), HPos.CENTER);
	}
}
