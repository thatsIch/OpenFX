package de.thatsich.bachelor.javafx.presentation.classification;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.EvaluationDatabase;
import de.thatsich.core.Log;

public class ClassificationDisplayPresenter implements Initializable {

	// Nodes
	@FXML private ImageView nodeImageViewMatrix;
	
	// Injects
	@SuppressWarnings("unused")
	@Inject private Log log;
	@SuppressWarnings("unused")
	@Inject private EvaluationDatabase evals;
	
	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		
	}

}
