package de.thatsich.openfx.errorgeneration.api.control.entity;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IError extends IEntity
{
	ReadOnlyStringProperty dateTimeProperty();

	ReadOnlyStringProperty clazzProperty();

	ReadOnlyStringProperty idProperty();

	ReadOnlyObjectProperty<Mat> originalProperty();

	ReadOnlyObjectProperty<Mat> errorProperty();

	ReadOnlyObjectProperty<Mat> modifiedProperty();

	@Override
	ErrorConfig getConfig();
}
