package de.thatsich.bachelor.errorgeneration.intern.service;

import de.thatsich.core.AConfigurationService;


/**
 * 
 * @author Tran Minh Do
 *
 */
public class ErrorConfigService extends AConfigurationService {

	/**
	 * 
	 */
	private static final String LAST_ERROR_ENTRY_INDEX = "last_error";
	private static final String LAST_ERROR_GENERATOR_INDEX = "last_error_generator";
	private static final String LAST_ERROR_LOOP_COUNT = "last_error_count";
	
	/**
	 * 
	 */
	private static final int DEFAULT_LAST_ERROR_ENTRY_INDEX = 0;
	private static final int DEFAULT_LAST_ERROR_GENERATOR_INDEX = 0;
	private static final int DEFAULT_LAST_ERROR_LOOP_COUNT = 1;
	
	// ================================================== 
	// Getter Implementation 
	// ==================================================	
	/**
	 * 
	 */
	public int getLastErrorEntryIndexInt() {
		return super.get(LAST_ERROR_ENTRY_INDEX, DEFAULT_LAST_ERROR_ENTRY_INDEX);
	}
	
	/**
	 * 
	 */
	public int getLastErrorGeneratorIndexInt() {
		return super.get(LAST_ERROR_GENERATOR_INDEX, DEFAULT_LAST_ERROR_GENERATOR_INDEX);
	}
	
	/**
	 * 
	 */
	public int getLastErrorCountInt() {
		return super.get(LAST_ERROR_LOOP_COUNT, DEFAULT_LAST_ERROR_LOOP_COUNT);
	}
	
	// ================================================== 
	// Setter Implementation 
	// ==================================================
	/**
	 * 
	 */
	public void setLastErrorEntryIndexInt(int lastErrorIndex) {
		super.set(LAST_ERROR_ENTRY_INDEX, lastErrorIndex);
	}

	/**
	 * 
	 */
	public void setLastErrorGeneratorIndexInt(int lastErrorGeneratorIndex) {
		super.set(LAST_ERROR_GENERATOR_INDEX, lastErrorGeneratorIndex);
	}

	/**
	 * 
	 */
	public void setLastErrorCountInt(int lastErrorCount) {
		super.set(LAST_ERROR_LOOP_COUNT, lastErrorCount);
	}
}
