package de.thatsich.bachelor.errorgeneration.restricted.command.commands;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.thatsich.bachelor.errorgeneration.api.entities.CircleError;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.api.entities.LineError;
import de.thatsich.core.javafx.ACommand;

public class InitErrorGeneratorListCommand extends ACommand<List<IErrorGenerator>> {

	@Inject private Injector injector;

    private <T extends IErrorGenerator> T get(Class<T> type) {
        return injector.getInstance(type);
    }
    
	@Override
	protected List<IErrorGenerator> call() throws Exception {
		final List<IErrorGenerator> errorGeneratorList = new ArrayList<IErrorGenerator>();
		
		errorGeneratorList.add(this.get(LineError.class));
		errorGeneratorList.add(this.get(CircleError.class));
		
		return errorGeneratorList;
	}
}
