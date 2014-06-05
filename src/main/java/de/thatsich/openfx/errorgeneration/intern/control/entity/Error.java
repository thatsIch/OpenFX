package de.thatsich.openfx.errorgeneration.intern.control.entity;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import org.opencv.core.Mat;


public class Error implements IError
{
	private final ReadOnlyObjectProperty<Mat> original;
	private final ReadOnlyObjectProperty<Mat> error;
	private final ReadOnlyObjectProperty<Mat> modified;
	private final ErrorConfig config;

	public Error(ErrorConfig config, Mat original, Mat modified, Mat error)
	{
		this.config = config;
		this.original = new ReadOnlyObjectWrapper<>(original);
		this.error = new ReadOnlyObjectWrapper<>(error);
		this.modified = new ReadOnlyObjectWrapper<>(modified);
	}

	@Override
	public ReadOnlyStringProperty dateTimeProperty()
	{
		return this.config.dateTime;
	}

	@Override
	public ReadOnlyStringProperty clazzProperty()
	{
		return this.config.clazz;
	}

	@Override
	public ReadOnlyStringProperty idProperty()
	{
		return this.config.id;
	}

	@Override
	public ReadOnlyObjectProperty<Mat> originalProperty()
	{
		return this.original;
	}

	@Override
	public ReadOnlyObjectProperty<Mat> errorProperty()
	{
		return this.error;
	}

	@Override
	public ReadOnlyObjectProperty<Mat> modifiedProperty()
	{
		return this.modified;
	}

	@Override
	public ErrorConfig getConfig()
	{
		return this.config;
	}
}
