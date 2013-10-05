package de.thatsich.bachelor.javafx.business.command;

public interface ICommandProvider {
	<T extends Object> T get(Class<T> type);
}
