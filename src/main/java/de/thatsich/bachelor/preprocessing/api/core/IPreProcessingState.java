package de.thatsich.bachelor.preprocessing.api.core;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;


/**
 * Model Interface
 * 
 * Grants Access to the PreProcessing State.
 * Getting the Property and set/get the Value directly
 * 
 * @author thatsIch
 */
public interface IPreProcessingState
{
	/**
	 * Property Getter for PreProcessor Folder-Path
	 * 
	 * @return Property for PreProcessor Folder-Path
	 */
	ObjectProperty<Path> getPreProcessorFolderPathProperty();

	/**
	 * Getter for PreProcessor Folder-Path
	 * 
	 * @return PreProcessor Folder-Path
	 */
	Path getPreProcessorFolderPath();

	/**
	 * Setter for PreProcessor Folder-Path
	 * 
	 * @param preProcessorFolderPath
	 *            to be set Path for PreProcessor
	 */
	void setPreProcessorFolderPath( Path preProcessorFolderPath );
}
