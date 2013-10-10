package de.thatsich.core.javafx;

public interface ICommandProvider {
	<T extends Object> T get(Class<T> type);
}
