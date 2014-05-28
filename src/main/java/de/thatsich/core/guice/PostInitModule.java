package de.thatsich.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Enables the guice application to use the PostInit Interface
 * to automatically call the init() function
 *
 * @author Minh
 */
public class PostInitModule extends AbstractModule
{

	/**
	 * AbstractModule Implementation
	 *
	 * wires all classes up
	 */
	@Override
	protected void configure()
	{
		super.bind(PostInitModule.class).toInstance(this);

		super.bindListener(new AbstractMatcher<TypeLiteral<?>>()
		                   {
			                   public boolean matches(TypeLiteral<?> typeLiteral)
			                   {
				                   return IPostInit.class.isAssignableFrom(typeLiteral.getRawType());
			                   }
		                   },

			new TypeListener()
			{
				@Override
				public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter)
				{
					typeEncounter.register(new InjectionListener<I>()
					{
						@Override
						public void afterInjection(Object i)
						{
							IPostInit m = (IPostInit) i;
							m.init();
						}
					});
				}
			});
	}

}
