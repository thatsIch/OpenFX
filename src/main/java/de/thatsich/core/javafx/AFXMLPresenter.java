package de.thatsich.core.javafx;

import javafx.fxml.Initializable;

import com.google.inject.Inject;

import de.thatsich.core.Log;

/**
 * Basic Wrapper Class for needed injections
 * to any presenter using FXML
 * * Logger: can log information and display them.
 * 
 * @author Minh
 *
 */
public abstract class AFXMLPresenter implements Initializable {
	
	// Injects
	@Inject protected Log log;
}
