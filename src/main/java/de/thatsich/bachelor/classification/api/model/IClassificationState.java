package de.thatsich.bachelor.classification.api.model;

import javafx.beans.property.ObjectProperty;

import java.nio.file.Path;


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
	ObjectProperty<Path> path();
}
