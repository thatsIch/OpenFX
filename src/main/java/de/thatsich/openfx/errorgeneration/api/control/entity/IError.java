package de.thatsich.openfx.errorgeneration.api.control.entity;

import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IError
{
	String getDateTime();

	String getClazz();

	String getId();

	Mat getOriginal();

	Mat getError();

	Mat getModified();
}
