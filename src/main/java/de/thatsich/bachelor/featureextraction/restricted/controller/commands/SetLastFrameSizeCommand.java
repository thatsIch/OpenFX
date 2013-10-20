package de.thatsich.bachelor.featureextraction.restricted.controller.commands;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.service.ConfigService;
import de.thatsich.core.javafx.Command;

public class SetLastFrameSizeCommand extends Command<Void> {

	// Properties
	private final IntegerProperty lastFrameSize = new SimpleIntegerProperty();
	
	// Injects
	@Inject ConfigService config;
	
	@Inject
	protected SetLastFrameSizeCommand(@Assisted int lastFrameSize) {
		this.lastFrameSize.set(lastFrameSize);
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			@Override protected Void call() throws Exception {
				config.setLastFrameSizeInt(lastFrameSize.get());
				
				return null;
			}
		};
	}
}
