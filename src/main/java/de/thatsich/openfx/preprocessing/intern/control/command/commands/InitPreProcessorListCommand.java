package de.thatsich.openfx.preprocessing.intern.control.command.commands;

import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.AANNPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.IdentityPreProcessor;
import de.thatsich.openfx.preprocessing.intern.control.command.preprocessor.core.IPreProcessor;
import de.thatsich.core.javafx.ACommand;

import java.util.ArrayList;
import java.util.List;


public class InitPreProcessorListCommand extends ACommand<List<IPreProcessor>>
{
	@Override
	protected List<IPreProcessor> call() throws Exception
	{
		final List<IPreProcessor> list = new ArrayList<>();

		list.add( this.get( IdentityPreProcessor.class ) );
		list.add(this.get(AANNPreProcessor.class));

		return list;
	}

	private <T extends IPreProcessor> T get(Class<T> type)
	{
		return super.injector.getInstance(type);
	}
}
