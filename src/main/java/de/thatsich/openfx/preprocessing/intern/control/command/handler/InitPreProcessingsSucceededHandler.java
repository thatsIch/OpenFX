package de.thatsich.openfx.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.preprocessing.api.control.IPreProcessing;
import de.thatsich.openfx.preprocessing.api.model.IPreProcessings;

import java.util.List;


/**
 * Handler for what should happen if the Command was successfull
 * for initializing the preprocessing get
 *
 * @author Minh
 */
public class InitPreProcessingsSucceededHandler extends ACommandHandler<List<IPreProcessing>>
{
	@Inject
	private IPreProcessings pps;

	@Override
	public void handle(List<IPreProcessing> value)
	{
		this.pps.list().addAll(value);
		this.log.info("Added " + value + " with Size " + value.size() + " to DataBase.");
	}
}
