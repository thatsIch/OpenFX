package de.thatsich.openfx.featureextraction.intern.control;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
@Entity
public class Adress implements Serializable
{
	final private String street;

	public Adress(final String street) {this.street = street;}

	public String getStreet()
	{
		return street;
	}

	@Override
	public String toString()
	{
		return this.street;
	}
}
