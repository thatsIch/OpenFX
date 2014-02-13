package de.thatsich.bachelor.preprocessing.api.models;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;


/**
 * Interface for PreProcessingState Model.
 * 
 * Enables access to the PreProcessing List Property and
 * has Getter, Setter and Property for the currently selected one
 * 
 * @author thatsIch
 */
public interface IPreProcessingState
{
	/**
	 * PropertyGetter of FolderPath
	 * 
	 * @return Property of FolderPath
	 */
	ObjectProperty<Path> getPreProcessingFolderPathProperty();

	/**
	 * Getter of FolderPath
	 * 
	 * @return FolderPath
	 */
	Path getPreProcessingFolderPath();

	/**
	 * Setter of FolderPath
	 * 
	 * @param folderPath
	 *            FolderPath
	 */
	void setPreProcessingFolderPath( Path folderPath );
}
