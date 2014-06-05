package de.thatsich.openfx.imageprocessing.api.control;

import de.thatsich.core.IEntity;
import de.thatsich.openfx.imageprocessing.intern.control.entity.ImageConfig;
import org.opencv.core.Mat;

/**
 * @author thatsIch
 * @since 04.06.2014.
 */
public interface IImage extends IEntity
{
	Mat getImageMat();

	String getImageName();

	@Override
	ImageConfig getConfig();
}
