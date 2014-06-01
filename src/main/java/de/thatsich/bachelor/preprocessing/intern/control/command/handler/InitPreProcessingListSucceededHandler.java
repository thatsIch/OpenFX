package de.thatsich.bachelor.preprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.preprocessing.api.control.IPreProcessing;
import de.thatsich.bachelor.preprocessing.api.model.IPreProcessings;
import de.thatsich.core.javafx.ACommandHandler;

import java.util.List;


/**
 * Handler for what should happen if the Command was successfull
 * for initializing the preprocessing list
 *
 * @author Minh
 */
public class InitPreProcessingListSucceededHandler extends ACommandHandler<List<IPreProcessing>>
{
	@Inject
	private IPreProcessings pps;

	@Override
	public void handle(List<IPreProcessing> value)
	{
		this.pps.getPreProcessingListProperty().addAll(value);
		this.log.info("Added " + value + " with Size " + value.size() + " to DataBase.");
	}
}
