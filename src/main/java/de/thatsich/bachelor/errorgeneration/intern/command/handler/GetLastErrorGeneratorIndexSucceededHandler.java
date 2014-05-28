package de.thatsich.bachelor.errorgeneration.intern.command.handler;

import com.google.inject.Inject;
import de.thatsich.bachelor.errorgeneration.api.core.IErrorGenerators;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.core.javafx.ACommandHandler;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastErrorGeneratorIndex
 *
 * @author Minh
 */
public class GetLastErrorGeneratorIndexSucceededHandler extends ACommandHandler<Integer>
{

	@Inject
	private IErrorGenerators errorGeneratorList;

	@Override
	public void handle(Integer value)
	{
		final IErrorGenerator selectedErrorGenerator = this.errorGeneratorList.getErrorGeneratorListProperty().get(value);
		this.errorGeneratorList.getSelectedErrorGeneratorProperty().set(selectedErrorGenerator);
		this.log.info("Set last selected error generator index in Model.");
	}
}
