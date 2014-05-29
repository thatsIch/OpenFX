package de.thatsich.bachelor.errorgeneration.intern.service;

import com.google.inject.Singleton;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.core.opencv.Images;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.nio.file.Path;


@Singleton
public class ErrorFactoryService
{
	public ErrorEntry getErrorEntryFromPath(Path directory)
	{
		final String unparsedString = directory.getFileName().toString();
		final String splitString[] = unparsedString.split("_");
		final String className = splitString[0];
		final String id = splitString[1];

		final Mat original = Images.toMat(directory.resolve("original.png"));
		final Mat modified = Images.toMat(directory.resolve("modified.png"));
		final Mat error = Images.toMat(directory.resolve("error.png"));

		return new ErrorEntry(directory, original, modified, error, className, id);
	}

	public ErrorEntry getErrorEntryFromMat(Path filePath, Mat originalMat, Mat originalWithErrorMat)
	{
		final String unparsedString = filePath.getFileName().toString();
		final String splitString[] = unparsedString.split("_");
		final String errorName = splitString[0];
		final String className = splitString[1];

		final Mat onlyErrorMat = new Mat();
		Core.absdiff(originalMat, originalWithErrorMat, onlyErrorMat);

		return new ErrorEntry(filePath, originalMat, originalWithErrorMat, onlyErrorMat, className, errorName);
	}
}
