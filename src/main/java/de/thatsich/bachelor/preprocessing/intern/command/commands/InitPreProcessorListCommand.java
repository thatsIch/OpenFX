package de.thatsich.bachelor.preprocessing.intern.command.commands;

import java.util.ArrayList;
import java.util.List;

import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.AANNPreProcessor;
import de.thatsich.bachelor.preprocessing.intern.command.preprocessor.core.IPreProcessor;
import de.thatsich.core.javafx.ACommand;


public class InitPreProcessorListCommand extends ACommand<List<IPreProcessor>>
{
	private <T extends IPreProcessor> T get( Class<T> type )
	{
		return super.injector.getInstance( type );
	}

	@Override
	protected List<IPreProcessor> call() throws Exception
	{
		final List<IPreProcessor> list = new ArrayList<IPreProcessor>();

		// list.add( this.get( IdentityPreProcessor.class ) );
		list.add( this.get( AANNPreProcessor.class ) );

		return list;
	}
}
