package de.thatsich.core;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logger which is shared across the whole application.
 * Examples are shown below:
 *
 * this.log.info("thats an info");
 * this.log.warning("thats a warning");
 * this.log.severe("das less pretty");
 * this.log.throwing(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), new Exception("test"));
 *
 * @author Minh
 */
public class Log extends Logger
{
	final private static Level level = Level.ALL;

	public Log()
	{
		super("", null);
		ConsoleHandler handler = new ConsoleHandler();
		LogFormatter formatter = new LogFormatter();

		// set log level
		this.setLevel(Log.level);
		handler.setLevel(Log.level);

		// remove default handler
		this.setUseParentHandlers(false);

		// link custom ones
		handler.setFormatter(formatter);
		this.addHandler(handler);
	}
}
