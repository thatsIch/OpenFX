package de.thatsich.bachelor.preprocessing.intern.model;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


/**
 * Model Class
 * 
 * Contains information about the state of the PreProcessing.
 * 
 * @author thatsIch
 */
public class PreProcessingState
{
	/**
	 * Property for the PreProcessor Folder-Path
	 */
	private final ObjectProperty<Path>	preProcessorFolderPath	= new SimpleObjectProperty<Path>();

	/**
	 * Property Getter for PreProcessor Folder-Path
	 * 
	 * @return Property for PreProcessor Folder-Path
	 */
	public ObjectProperty<Path> getPreProcessorFolderPathProperty()
	{
		return this.preProcessorFolderPath;
	}

	/**
	 * Getter for PreProcessor Folder-Path
	 * 
	 * @return PreProcessor Folder-Path
	 */
	public Path getPreProcessorFolderPath()
	{
		return this.preProcessorFolderPath.get();
	}

	/**
	 * Setter for PreProcessor Folder-Path
	 * 
	 * @param preProcessorFolderPath
	 *            to be set Path for PreProcessor
	 */
	public void setPreProcessorFolderPath( Path preProcessorFolderPath )
	{
		this.preProcessorFolderPath.set( preProcessorFolderPath );
	}
}
