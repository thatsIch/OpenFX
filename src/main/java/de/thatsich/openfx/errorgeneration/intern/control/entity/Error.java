package de.thatsich.openfx.errorgeneration.intern.control.entity;

import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import org.opencv.core.Mat;


public class Error implements IError
{
	private final Mat original;
	private final Mat error;
	private final Mat modified;

	private final String dateTime;
	private final String clazz;
	private final String id;

	public Error(Mat original, Mat modified, Mat error, String dateTime, String clazz, String id)
	{
		this.original = original;
		this.error = error;
		this.modified = modified;
		this.dateTime = dateTime;
		this.clazz = clazz;
		this.id = id;
	}

	@Override
	public String getDateTime()
	{
		return this.dateTime;
	}

	@Override
	public String getClazz()
	{
		return this.clazz;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public Mat getOriginal()
	{
		return this.original;
	}

	@Override
	public Mat getError()
	{
		return this.error;
	}

	@Override
	public Mat getModified()
	{
		return this.modified;
	}
}
