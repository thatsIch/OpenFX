package de.thatsich.openfx.imageprocessing.intern.control.command.handler;

import com.google.inject.Inject;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.core.javafx.ACommandHandler;

import java.nio.file.Path;

public class GetLastLocationSucceededHandler extends ACommandHandler<Path>
{

	@Inject
	private IImageState imageState;

	@Override
	public void handle(Path value)
	{
		this.imageState.setLastLocation(value);
	}
}
