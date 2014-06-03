package de.thatsich.openfx.errorgeneration.api.model;

import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorGenerators
{
	ListProperty<IErrorGenerator> errorGenerators();

	ObjectProperty<IErrorGenerator> selectedErrorGenerator();
}
