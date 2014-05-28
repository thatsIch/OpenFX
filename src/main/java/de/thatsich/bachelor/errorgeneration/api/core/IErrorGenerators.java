package de.thatsich.bachelor.errorgeneration.api.core;

import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorGenerators
{
	public ListProperty<IErrorGenerator> getErrorGeneratorListProperty();

	public ObjectProperty<IErrorGenerator> getSelectedErrorGeneratorProperty();

	public IErrorGenerator getSelectedErrorGenerator();

	public void setSelectedErrorGenerator(IErrorGenerator generator);
}
