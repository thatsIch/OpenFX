package de.thatsich.bachelor.preprocessing.intern.service;

import lombok.Getter;


public enum EPreProcessingConfigType
{
	LAST_PRE_PROCESSOR_INDEX( 0 ),
	LAST_PRE_PROCESSING_INDEX( 0 );

	@Getter private final int	defaultValue;

	private EPreProcessingConfigType( int defaultValue )
	{
		this.defaultValue = defaultValue;
	}
}
