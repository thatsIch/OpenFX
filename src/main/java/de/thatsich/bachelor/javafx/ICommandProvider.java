package de.thatsich.bachelor.javafx;

public interface ICommandProvider {
	<T extends Object> T get(Class<T> type);
}
