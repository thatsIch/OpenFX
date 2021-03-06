package de.thatsich.openfx.preprocessing.intern.control.command.service;

import de.thatsich.core.AConfigurationService;


public class PreProcessingConfigService extends AConfigurationService
{
	public void set(EPreProcessingConfigType type, int value)
	{
		super.set(type.toString(), value);
	}

	public int get(EPreProcessingConfigType type)
	{
		return super.get(type.toString(), type.defaultValue);
	}
}
