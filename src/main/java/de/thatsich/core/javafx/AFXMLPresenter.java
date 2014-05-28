package de.thatsich.core.javafx;

import com.google.inject.Inject;
import de.thatsich.core.Log;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Basic Wrapper Class for needed injections
 * to any presenter using FXML
 * * Logger: can log information and display them.
 *
 * @author Minh
 */
public abstract class AFXMLPresenter implements Initializable
{

	// Injects
	@Inject
	protected Log log;

	@Override
	public final void initialize(URL paramURL, ResourceBundle paramResourceBundle)
	{
		this.bindComponents();
		this.initComponents();
	}

	protected abstract void bindComponents();

	protected abstract void initComponents();
}
