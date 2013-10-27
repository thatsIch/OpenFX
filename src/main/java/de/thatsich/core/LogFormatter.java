package de.thatsich.core;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Handles the formatting of the Logger
 * Tries to pad the message
 * 
 * @author Minh
 *
 */
public class LogFormatter extends Formatter {

	final private String MESSAGE_PATTERN = "[%tT] [%-7s] %s%s > %s %s\n";

	/**
	 * Displays simple message with
	 * - timestamp
	 * - message-type
	 * - source
	 * - message
	 */
	@Override
	public String format(final LogRecord log) {
		int classIndex = log.getSourceClassName().lastIndexOf(".") + 1;
		String className = log.getSourceClassName().substring(classIndex) + ".";

		return String.format(
			this.MESSAGE_PATTERN, 
			new Date(log.getMillis()),
			log.getThrown() == null ? log.getLevel() : "THROW",
			className,
			log.getSourceMethodName(),
			log.getMessage(),
			log.getThrown() == null ? "" : log.getThrown()
		);
	}

}
