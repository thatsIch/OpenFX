package de.thatsich.openfx.preprocessing.intern.model;

import com.google.inject.Singleton;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessingState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.nio.file.Path;

@Singleton
public class PreProcessingState implements IPreProcessingState
{
	// Properties
	private final ObjectProperty<Path> path = new SimpleObjectProperty<>();

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
	public void setPreProcessingFolderPath(Path folderPath)
	{
		this.path.set(folderPath);
	}

}
