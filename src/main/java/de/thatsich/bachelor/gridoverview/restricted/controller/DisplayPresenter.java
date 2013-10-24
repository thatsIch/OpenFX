package de.thatsich.bachelor.gridoverview.restricted.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.thatsich.bachelor.classificationtesting.restricted.views.TestDisplayView;
import de.thatsich.bachelor.classificationtesting.restricted.views.TestInputView;
import de.thatsich.bachelor.classificationtesting.restricted.views.TestListView;
import de.thatsich.bachelor.classificationtraining.restricted.views.TrainDisplayView;
import de.thatsich.bachelor.classificationtraining.restricted.views.TrainInputView;
import de.thatsich.bachelor.classificationtraining.restricted.views.TrainListView;
import de.thatsich.bachelor.errorgeneration.restricted.views.ErrorDisplayView;
import de.thatsich.bachelor.errorgeneration.restricted.views.ErrorInputView;
import de.thatsich.bachelor.errorgeneration.restricted.views.ErrorListView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureDisplayView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureInputView;
import de.thatsich.bachelor.featureextraction.restricted.views.FeatureListView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageDisplayView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageInputView;
import de.thatsich.bachelor.imageprocessing.restricted.views.ImageListView;

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
	@Inject private ImageListView imageListView;
	
	@Inject private ErrorInputView errorInputView;
	@Inject private ErrorDisplayView errorDisplayView;
	@Inject private ErrorListView errorListView;
	
	@Inject private FeatureInputView featureInputView;
	@Inject private FeatureDisplayView featureDisplayView;
	@Inject private FeatureListView featureListView;
	
	@Inject private TrainInputView trainInputView;
	@Inject private TrainDisplayView trainDisplayView;
	@Inject private TrainListView trainListView;
	
	@Inject private TestInputView testInputView;
	@Inject private TestDisplayView testDisplayView;
	@Inject private TestListView testListView;
	
	// ================================================== 
	// Initializable Implementation 
	// ==================================================
	@Override public void initialize(URL url, ResourceBundle resourceBundle) {
		
		this.nodeRoot.add(this.imageInputView.getRoot(), 0, 1);
		this.nodeRoot.add(this.imageDisplayView.getRoot(), 0, 2);
		this.nodeRoot.add(this.imageListView.getRoot(), 0, 3);
		
		this.nodeRoot.add(this.errorInputView.getRoot(), 1, 1);
		this.nodeRoot.add(this.errorDisplayView.getRoot(), 1, 2);
		this.nodeRoot.add(this.errorListView.getRoot(), 1, 3);
		
		this.nodeRoot.add(this.featureInputView.getRoot(), 2, 1);
		this.nodeRoot.add(this.featureDisplayView.getRoot(), 2, 2);
		this.nodeRoot.add(this.featureListView.getRoot(), 2, 3);
		
		this.nodeRoot.add(this.trainInputView.getRoot(), 3, 1);
		this.nodeRoot.add(this.trainDisplayView.getRoot(), 3, 2);
		this.nodeRoot.add(this.trainListView.getRoot(), 3, 3);
//		
		this.nodeRoot.add(this.testInputView.getRoot(), 4, 1);
		this.nodeRoot.add(this.testDisplayView.getRoot(), 4, 2);
		this.nodeRoot.add(this.testListView.getRoot(), 4, 3);
		
		// Set alignment for all children
		for (Node child : this.nodeRoot.getChildren()) {
			GridPane.setValignment(child, VPos.TOP);
			GridPane.setHalignment(child, HPos.CENTER);
		}
	}
}