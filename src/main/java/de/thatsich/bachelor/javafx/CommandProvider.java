package de.thatsich.bachelor.javafx;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class CommandProvider implements ICommandProvider {

	@Inject
	private Injector injector;
	
	@Override
	public <T extends Object> T get(Class<T> type) {
		return this.injector.getInstance(type);
	}

}
