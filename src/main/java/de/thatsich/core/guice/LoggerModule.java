package de.thatsich.core.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import de.thatsich.core.Log;


/**
 * Includes a logger instance into your guice application
 * 
 * @author Minh
 *
 */
public class LoggerModule extends AbstractModule {

	/**
	 * AbstractModule Implementation
	 * 
	 * Used for bindings classes together
	 */
	@Override
	protected void configure() {
		super.bind(LoggerModule.class).toInstance(this);

		super.bind(Log.class).in(Scopes.SINGLETON);
	}

}
