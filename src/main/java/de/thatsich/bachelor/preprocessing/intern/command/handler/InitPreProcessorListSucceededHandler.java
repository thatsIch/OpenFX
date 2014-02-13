package de.thatsich.bachelor.preprocessing.intern.command.handler;

import java.util.List;

import com.google.inject.Inject;

import de.thatsich.bachelor.preprocessing.api.models.IPreProcessors;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;
import de.thatsich.core.javafx.ACommandHandler;


public class InitPreProcessorListSucceededHandler extends ACommandHandler<List<IPreProcessor>>
{
	@Inject private IPreProcessors	pps;

	@Override
	public void handle( List<IPreProcessor> value )
	{
		this.pps.getPreProcessorListProperty().addAll( value );
		this.log.info( "Added " + value + " to DataBase." );
	}
}
