package de.thatsich.core;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface IFileStorageService<T>
{
	void save(T elem);

	T load(Path path);
}
