package de.thatsich.core.javafx;

import com.cathive.fx.guice.GuiceFXMLLoader;
import com.cathive.fx.guice.GuiceFXMLLoader.Result;
import com.google.inject.Inject;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;


/**
 * all view have a parent object they will retrieve
 * fetches the name from its own child-name
 *
 * @author Tran Minh Do
 */
public abstract class AFXMLView implements IFXMLView
{
	/**
	 * Parent Object of the FXML
	 */
	private Parent pane;

	/**
	 * FXML Loader
	 */
	@Inject private GuiceFXMLLoader loader;

	/*
	 * ==================================================
	 * Getter Implementation
	 * ==================================================
	 */
	@Override
	public Parent getRoot()
	{
		// lazy loading
		if (this.pane == null)
		{
			// fetch FXML
			final String fxml = this.getFXMLName();
			URL urlFXML = this.getClass().getResource(fxml);
			if (urlFXML == null)
			{
				throw new IllegalStateException("Could not find urlFXML");
			}

			// retrieve FXML
			Result res;
			try
			{
				res = this.loader.load(urlFXML);
			}
			catch (IOException e)
			{
				throw new IllegalStateException("Could not retrieve " + urlFXML + " from " + this.getClass().getSimpleName(), e);
			}

			this.pane = res.getRoot();

			// apply CSS if found
			this.applyCSSIfPossible(this.pane);
		}

		return this.pane;
	}

	/**
	 * getFXMLName
	 * FXMLName derived from SimpleClassName
	 *
	 * @return String FXML File Name
	 */
	private String getFXMLName()
	{
		return this.getSimpleClassName() + ".fxml";
	}

	/**
	 * applyCSSIfPossible
	 * checks if CSS File is avaiable and applies it
	 *
	 * @param parent Node which CSS is to be applied
	 */
	private void applyCSSIfPossible(Parent parent)
	{
		final URL url = this.getClass().getResource(this.getCSSName());
		if (url == null)
		{
			return;
		}

		final String uri = url.toExternalForm();
		parent.getStylesheets().add(uri);
	}

	/**
	 * Returns the simple ClassName
	 * e.g
	 * - Class: /path/to/ThisIsATest.class
	 * - Return: ThisIsATest
	 *
	 * @return String Simple Class Name
	 */
	private String getSimpleClassName()
	{
		return this.getClass().getSimpleName();
	}

	/**
	 * getCSSName
	 * gets the CSS File Name derived from SimpleClassName
	 *
	 * @return String CSS File Name
	 */
	private String getCSSName()
	{
		return this.getSimpleClassName() + ".css";
	}
}
