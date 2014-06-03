package de.thatsich.openfx.featureextraction.intern.control;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.io.Serializable;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
@Entity
public class Person implements Serializable
{
	@OneToOne(cascade = CascadeType.PERSIST)
	final private Adress adress;

	public Person(final Adress adress) {this.adress = adress;}

	public Adress getAdress()
	{
		return adress;
	}

	@Override
	public String toString()
	{
		return this.adress.toString();
	}
}
