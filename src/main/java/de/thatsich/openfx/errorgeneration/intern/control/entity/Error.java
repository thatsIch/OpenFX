package de.thatsich.openfx.errorgeneration.intern.control.entity;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import org.opencv.core.Mat;


public class Error implements IError
{
	private final ReadOnlyObjectWrapper<Mat> original;
	private final ReadOnlyObjectWrapper<Mat> error;
	private final ReadOnlyObjectWrapper<Mat> modified;

	private final ReadOnlyStringWrapper dateTime;
	private final ReadOnlyStringWrapper clazz;
	private final ReadOnlyStringWrapper id;

	public Error(Mat original, Mat modified, Mat error, String dateTime, String clazz, String id)
	{
		this.original = new ReadOnlyObjectWrapper<>(original);
		this.error = new ReadOnlyObjectWrapper<>(error);
		this.modified = new ReadOnlyObjectWrapper<>(modified);
		this.dateTime = new ReadOnlyStringWrapper(dateTime);
		this.clazz = new ReadOnlyStringWrapper(clazz);
		this.id = new ReadOnlyStringWrapper(id);
	}

	@Override
	public ReadOnlyStringProperty dateTimeProperty()
	{
		return this.dateTime.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty clazzProperty()
	{
		return this.clazz.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyStringProperty idProperty()
	{
		return this.id.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<Mat> originalProperty()
	{
		return this.original.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<Mat> errorProperty()
	{
		return this.error.getReadOnlyProperty();
	}

	@Override
	public ReadOnlyObjectProperty<Mat> modifiedProperty()
	{
		return this.modified.getReadOnlyProperty();
	}
}
