package de.thatsich.bachelor.classification.api.models;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;


/**
 * Interface for ClassifcationState Model.
 * 
 * Enables access to the Binary Classification List Property and
 * has Getter, Setter and Property for the currently selected one
 * 
 * @author thatsIch
 */
public interface IClassificationState
{
	/**
	 * PropertyGetter of FolderPath
	 * 
	 * @return Property of FolderPath
	 */
	public ObjectProperty<Path> getBinaryClassifierFolderPathProperty();
	
	/**
	 * Getter of FolderPath
	 * 
	 * @return FolderPath
	 */
	public Path getBinaryClassifierFolderPath();

	/**
	 * Setter of FolderPath
	 * 
	 * @param binaryClassifierFolderPath FolderPath
	 */
	public void setBinaryClassifierFolderPath( Path binaryClassifierFolderPath );

}
