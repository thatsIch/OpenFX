package de.thatsich.core.guice;

import com.google.inject.AbstractModule;

/**
 * Enables the guice application to use the PostInit Interface 
 * to automatically call the init() function
 * 
 * @author Minh
 *
 */
public class PostInitModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * wires all classes up
	 */
	@Override
	protected void configure() {
		super.bind(PostInitModule.class).toInstance(this);
		
//		super.bindListener(Matchers.subclassesOf(PostInit.class), new TypeListener() {
//		    @Override
//		    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
//		        typeEncounter.register(new InjectionListener<I>() {
//		            @Override
//		            public void afterInjection(Object i) {
//		            	PostInit m = (PostInit) i;
//		                m.init();
//		            }
//		        });
//		    }
//		});
	}

}
