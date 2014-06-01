package de.thatsich.bachelor.preprocessing.intern.control.command.service;

public enum EPreProcessingConfigType
{
	LAST_PRE_PROCESSOR_INDEX(0),
	LAST_PRE_PROCESSING_INDEX(0);

	private final int defaultValue;

	private EPreProcessingConfigType(int defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public int getDefaultValue() { return this.defaultValue; }
}
