package de.thatsich.bachelor.errorgeneration.api.core;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;

public interface IErrorGenerators {
	public ListProperty<IErrorGenerator> getErrorGeneratorListProperty();
	public ObjectProperty<IErrorGenerator> getSelectedErrorGeneratorProperty();
	public IErrorGenerator getSelectedErrorGenerator();
	public void setSelectedErrorGenerator(IErrorGenerator generator);
}
