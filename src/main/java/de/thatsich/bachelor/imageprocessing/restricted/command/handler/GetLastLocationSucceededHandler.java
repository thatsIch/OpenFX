package de.thatsich.bachelor.imageprocessing.restricted.command.handler;

import java.nio.file.Path;

import com.google.inject.Inject;

import de.thatsich.bachelor.imageprocessing.api.core.IImageState;
import de.thatsich.core.javafx.ACommandHandler;

public class GetLastLocationSucceededHandler extends ACommandHandler<Path> {

	@Inject private IImageState imageState;
	
	@Override
	public void handle(Path value) {
		this.imageState.setLastLocation(value);
	}
}
