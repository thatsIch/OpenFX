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
	ReadOnlyStringProperty dateTimeProperty();

	ReadOnlyStringProperty clazzProperty();

	ReadOnlyStringProperty idProperty();

	ReadOnlyObjectProperty<Mat> originalProperty();

	ReadOnlyObjectProperty<Mat> errorProperty();

	ReadOnlyObjectProperty<Mat> modifiedProperty();
}
