package de.thatsich.core.javafx;

import javafx.scene.Parent;

/**
 * Abstract Interface for Views build on FXML.
 * Enables access to the root element via getRoot().
 * 
 * @author thatsIch
 */
public abstract interface IFXMLView {
	abstract Parent getRoot();
}
