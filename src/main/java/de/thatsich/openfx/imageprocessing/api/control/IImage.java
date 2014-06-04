package de.thatsich.openfx.imageprocessing.api.control;

import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IImage
{
	Mat getImageMat();

	String getImageName();
}
