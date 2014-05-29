package de.thatsich.bachelor.errorgeneration.intern.service;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.opencv.Images;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class ErrorStorageService
{
	public void store(ErrorEntry entry) throws IOException
	{
		this.createInvalidDirectory(entry.getStoragePath());

		Images.store(entry.getOriginalMat(), entry.getStoragePath().resolve("original.png"));
		Images.store(entry.getOriginalWithErrorMat(), entry.getStoragePath().resolve("modified.png"));
		Images.store(entry.getErrorMat(), entry.getStoragePath().resolve("error.png"));
	}

	/**
	 * checks if either the directory exists and if its a directory
	 *
	 * @param directory to be created directory
	 */
	private void createInvalidDirectory(Path directory) throws IOException
	{
		if (Files.notExists(directory) || !Files.isDirectory(directory))
		{
			Files.createDirectories(directory);
		}
	}
}
