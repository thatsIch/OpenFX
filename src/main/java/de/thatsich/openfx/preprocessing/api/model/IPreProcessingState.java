package de.thatsich.openfx.preprocessing.api.model;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;


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
	ObjectProperty<Path> path();
}
