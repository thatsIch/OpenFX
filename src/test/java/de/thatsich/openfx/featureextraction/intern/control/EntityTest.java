package de.thatsich.openfx.featureextraction.intern.control;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author thatsIch
 * @since 03.06.2014.
 */
@RunWith(JukitoRunner.class)
public class EntityTest
{
	@Test
	public void testEntity()
	{
		final Adress oldA = new Adress("home");
		final Person oldP = new Person(oldA);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("io/persons.odb");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(oldP);
		em.getTransaction().commit();

		TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
		List<Person> results = query.getResultList();
		em.getTransaction().begin();
		results.forEach(p -> {
			em.remove(p);
			System.out.println("Removed " + p.toString());
		});
		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}
