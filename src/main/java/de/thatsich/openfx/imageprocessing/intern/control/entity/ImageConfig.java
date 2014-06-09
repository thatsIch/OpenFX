package de.thatsich.openfx.imageprocessing.intern.control.entity;

import de.thatsich.core.IEntityConfig;

/**
 * @author thatsIch
 * @since 05.06.2014.
 */
public class ImageConfig implements IEntityConfig
{
	public final String imageName;

	public ImageConfig(final String imageName) {this.imageName = imageName;}

	@Override
	public String toString()
	{
		return this.imageName;
	}
}
