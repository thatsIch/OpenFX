package de.thatsich.core;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * CRUD interface
 *
 * - CREATE
 * - RETRIEVE
 * - UPDATE
 * - DELETE
 *
 * @author thatsIch
 * @since 01.06.2014.
 */
public interface IFileStorageService<T>
{
	List<T> init() throws IOException;

	T create(T elem) throws IOException;

	T retrieve(Path path) throws IOException;

	T update(T elem) throws IOException;

	void delete(T elem) throws IOException;
}
