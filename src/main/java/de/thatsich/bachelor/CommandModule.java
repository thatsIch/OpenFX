package de.thatsich.bachelor;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.thatsich.bachelor.javafx.business.command.CommandFactory;

public class CommandModule extends AbstractModule {

	@Override
	public void configure() {
		this.install(
			new FactoryModuleBuilder()
			.build(CommandFactory.class)
		);
	}
}
