package de.thatsich.openfx.errorgeneration.api.model;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;

public interface IErrors
{
	ListProperty<IError> list();

	ObjectProperty<IError> selected();
}
