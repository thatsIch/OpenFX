package de.thatsich.openfx.errorgeneration.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommandHandler;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.errorgeneration.api.model.IErrorGenerators;

/**
 * Handler for what should happen if the Command was successfull
 * for getting LastErrorGeneratorIndex
 *
 * @author Minh
 */
public class GetLastErrorGeneratorIndexSucceededHandler extends ACommandHandler<Integer>
{
	@Inject private IErrorGenerators errorGeneratorList;

	@Override
	public void handle(Integer value)
	{
		final IErrorGenerator selectedErrorGenerator = this.errorGeneratorList.list().get(value);
		this.errorGeneratorList.selected().set(selectedErrorGenerator);
		this.log.info("Set last selected error generator index in Model.");
	}
}
