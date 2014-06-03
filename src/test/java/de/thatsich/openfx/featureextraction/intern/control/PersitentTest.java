package de.thatsich.openfx.featureextraction.intern.control;

import com.objectdb.Utilities;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.jdo.PersistenceManager;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
@RunWith(JukitoRunner.class)
public class PersitentTest
{
	@Test
	public void persistent()
	{
		PersistenceManager pm = Utilities.getPersistenceManager("person.db");
		pm.currentTransaction().begin();

		Utilities.bind(pm, new Person(new Adress("home")), "Hello World");

		pm.currentTransaction().commit();
		pm.close();

		assert true;
	}
}