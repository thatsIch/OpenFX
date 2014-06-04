package de.thatsich.openfx.errorgeneration.api.model;

import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrorGenerators
{
	ListProperty<IErrorGenerator> list();

	ObjectProperty<IErrorGenerator> selected();
}
