package de.thatsich.core;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface IFileStorageService<T>
{
	void save(T elem) throws IOException;

	T load(Path path) throws IOException;
}
