package de.thatsich.core.javafx;

import com.google.inject.Inject;

import de.thatsich.core.Log;

/**
 * Basic Wrapper Class for needed injections
 * to any CommandService
 * * Logger: can log information and display them.
 * * CommandProvider: can fetch commands and execute them.
 * 
 * @author Minh
 *
 */
public abstract class ACommandService {
	
	// Injects
	@Inject protected Log log;
	@Inject protected CommandProvider commandProvider;
}
