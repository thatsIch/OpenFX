package com.thatsich.sample.javafx.command;

public interface ICommandProvider {
	<T extends Object> T get(Class<T> type);
}
