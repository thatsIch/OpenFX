package de.thatsich.openfx.errorgeneration.api.control.entity;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IError
{
	ReadOnlyStringProperty getDateTime();

	ReadOnlyStringProperty getClazz();

	ReadOnlyStringProperty getId();

	ReadOnlyObjectProperty<Mat> getOriginal();

	ReadOnlyObjectProperty<Mat> getError();

	ReadOnlyObjectProperty<Mat> getModified();
}
