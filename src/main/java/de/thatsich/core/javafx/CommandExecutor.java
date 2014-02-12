package de.thatsich.core.javafx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandExecutor {

	private static final int maxThreadCount = Runtime.getRuntime().availableProcessors();
	
	public static ExecutorService newFixedThreadPool(int size) {
		return Executors.newFixedThreadPool(Math.min(maxThreadCount, size));
	}
}
