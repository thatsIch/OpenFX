package de.thatsich.core.guice;

import com.google.inject.AbstractModule;

import de.thatsich.core.Log;


/**
 * Includes a logger instance into your guice application
 * 
 * @author Minh
 *
 */
public class LoggerModule extends AbstractModule {

	/**
	 * the injected logger
	 */
	final private Log log;
	
	
	/**
	 * CTOR
	 * 
	 * instantiates the logger
	 */
	public LoggerModule() {
		this.log = new Log();
	}
	
	/**
	 * AbstractModule Implementation
	 * 
	 * Used for bindings classes together
	 */
	@Override
	protected void configure() {
		super.bind(LoggerModule.class).toInstance(this);
		
		super.bind(Log.class).toInstance(this.log);
	}

}
