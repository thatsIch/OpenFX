package de.thatsich.bachelor.preprocessing.intern.model;

import java.nio.file.Path;

import com.google.inject.Singleton;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import de.thatsich.bachelor.preprocessing.api.models.IPreProcessingState;

@Singleton
public class PreProcessingState implements IPreProcessingState
{
	// Properties
	private final ObjectProperty<Path> path = new SimpleObjectProperty<Path>();
	
	@Override
	public ObjectProperty<Path> getPreProcessingFolderPathProperty()
	{
		return this.path;
	}

	@Override
	public Path getPreProcessingFolderPath()
	{
		return this.path.get();
	}

	@Override
	public void setPreProcessingFolderPath( Path folderPath )
	{
		this.path.set( folderPath );
	}

}
