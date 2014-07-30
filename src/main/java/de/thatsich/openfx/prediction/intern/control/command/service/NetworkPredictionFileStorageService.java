package de.thatsich.openfx.prediction.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.core.IEntityConfig;
import de.thatsich.core.opencv.Images;
import de.thatsich.openfx.errorgeneration.api.control.entity.IError;
import de.thatsich.openfx.errorgeneration.intern.control.entity.Error;
import de.thatsich.openfx.errorgeneration.intern.control.entity.ErrorConfig;
import de.thatsich.openfx.prediction.api.control.entity.INetworkPrediction;
import de.thatsich.openfx.prediction.api.model.IPredictionState;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPrediction;
import de.thatsich.openfx.prediction.intern.control.entity.NetworkPredictionConfig;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class NetworkPredictionFileStorageService extends AFileStorageService<INetworkPrediction>
{
	@Inject
	protected NetworkPredictionFileStorageService(IPredictionState state)
	{
		super(state.path().get());
	}

	@Override
	public List<INetworkPrediction> init() throws IOException
	{
		final List<INetworkPrediction> binaryPredictions = new LinkedList<>();

		// traverse whole directory and search for yaml files
		// try to open them
		// and parse the correct classifier
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath))
		{
			for (Path child : stream)
			{
				final INetworkPrediction prediction = this.retrieve(child);
				binaryPredictions.add(prediction);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All NetworkPredictions extracted.");

		return binaryPredictions;
	}

	@Override
	public INetworkPrediction create(INetworkPrediction prediction)
	{
		final NetworkPredictionConfig config = prediction.getConfig();
		final String fileName = config.toString();
		final Path dirPath = super.storagePath.resolve(fileName);
		super.createInvalidDirectory(dirPath);

		final Mat modified = prediction.modified().get();
		Images.store(modified, dirPath.resolve("modified.png"));
		this.log.info("Storing modified");

		try
		{
			final Size size = modified.size();
			this.log.info("whata " + size);
			final IError[][] errors = prediction.errors().get();
			this.log.info("Resolving error dependencies.");

			this.createErrors(dirPath.resolve("error"), errors, size);
			this.createErrorClasses(dirPath.resolve("errorClasses.csv"), prediction.errorClasses().get());
			this.createErrorPredictions(dirPath.resolve("errorPredictions.csv"), prediction.errorPredictions().get());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return prediction;
	}

	private void createErrors(Path path, IError[][] errors, Size size)
	{
		try
		{
			super.createInvalidDirectory(path);
			this.log.info("Trying to create directory.");

			final Mat[][] originalSplit = this.extractOriginal(errors);
			final Mat[][] modifiedSplit = this.extractModified(errors);
			final Mat[][] errorSplit = this.extractError(errors);
			this.log.info("Extracting images");

			final Mat original = this.assembleImage(originalSplit, size);
			final Mat modified = this.assembleImage(modifiedSplit, size);
			final Mat error = this.assembleImage(errorSplit, size);
			this.log.info("Assembling images.");

			Images.store(original, path.resolve("original.png"));
			Images.store(modified, path.resolve("modified.png"));
			Images.store(error, path.resolve("error.png"));
			this.log.info("Storing images");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void createErrorClasses(Path path, String[][] strings)
	{
		final int rows = strings[0].length;

		final StringJoiner vectorJoiner = new StringJoiner(System.lineSeparator());
		for (final String[] string : strings)
		{
			final StringJoiner rowJoiner = new StringJoiner(",");
			for (int row = 0; row < rows; row++)
			{
				rowJoiner.add(string[row]);
			}
			vectorJoiner.add(rowJoiner.toString());
		}

		try (BufferedWriter vectorWriter = Files.newBufferedWriter(path, StandardCharsets.US_ASCII))
		{
			vectorWriter.write(vectorJoiner.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void createErrorPredictions(Path path, Double[][] doubles)
	{
		this.log.info("Error Prediction Size " + doubles.length + "," + doubles[0].length);
		final int rows = doubles[0].length;

		final StringJoiner vectorJoiner = new StringJoiner(System.lineSeparator());
		for (final Double[] string : doubles)
		{
			final StringJoiner rowJoiner = new StringJoiner(",");
			for (int row = 0; row < rows; row++)
			{
				rowJoiner.add(String.valueOf(string[row]));
			}
			vectorJoiner.add(rowJoiner.toString());
		}

		try (BufferedWriter vectorWriter = Files.newBufferedWriter(path, StandardCharsets.US_ASCII))
		{
			vectorWriter.write(vectorJoiner.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private Mat[][] extractOriginal(IError[][] errors)
	{
		final int cols = errors.length;
		final int rows = errors[0].length;

		final Mat[][] result = new Mat[cols][rows];

		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				result[col][row] = errors[col][row].originalProperty().get();
			}
		}

		return result;
	}

	private Mat[][] extractModified(IError[][] errors)
	{
		final int cols = errors.length;
		final int rows = errors[0].length;

		final Mat[][] result = new Mat[cols][rows];

		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				result[col][row] = errors[col][row].modifiedProperty().get();
			}
		}

		return result;
	}

	private Mat[][] extractError(IError[][] errors)
	{
		final int cols = errors.length;
		final int rows = errors[0].length;

		final Mat[][] result = new Mat[cols][rows];

		for (int col = 0; col < cols; col++)
		{
			for (int row = 0; row < rows; row++)
			{
				result[col][row] = errors[col][row].errorProperty().get();
			}
		}

		return result;
	}

	private Mat assembleImage(Mat[][] split, Size assembledSize)
	{
		final Mat assembly = Mat.zeros(assembledSize, CvType.CV_8UC1);

		for (int col = 0; col < split.length; col++)
		{
			for (int row = 0; row < split[col].length; row++)
			{
				final Mat splitPart = split[col][row];
				final int frameSize = splitPart.rows();
				final Mat roi = assembly.submat(frameSize * row, frameSize * (row + 1), frameSize * col, frameSize * (col + 1));
				splitPart.copyTo(roi);
			}
		}

		return assembly;
	}

	@Override
	public INetworkPrediction retrieve(Path filePath)
	{
		final String fileName = filePath.getFileName().toString();

		final NetworkPredictionConfig config = new NetworkPredictionConfig(fileName);
		final Mat modified = Images.toMat(filePath.resolve("modified.png"));
		final IError[][] errors = this.retrieveErrors(filePath.resolve("error"));
		final String[][] errorClasses = this.retrieveErrorClasses(filePath.resolve("errorClasses.csv"));
		final Double[][] errorPredictions = this.retrieveErrorPredictions(filePath.resolve("errorPredictions.csv"));

		return new NetworkPrediction(config, modified, errors, errorClasses, errorPredictions);
	}

	@Override
	public INetworkPrediction update(final INetworkPrediction elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(final INetworkPrediction prediction) throws IOException
	{
		final IEntityConfig config = prediction.getConfig();
		final String fileName = config.toString();
		final Path path = super.storagePath.resolve(fileName);

		super.deleteRecursively(path);
	}

	private IError[][] retrieveErrors(Path path)
	{
		final ErrorConfig config = new ErrorConfig("", "", "");

		final Mat original = Images.toMat(path.resolve("original.png"));
		final Mat modified = Images.toMat(path.resolve("modified.png"));
		final Mat error = Images.toMat(path.resolve("error.png"));

		final IError toBeSplit = new Error(config, original, modified, error);

		return this.splitError(toBeSplit);
	}

	private String[][] retrieveErrorClasses(Path path)
	{
		final List<String[]> errorClasses = new LinkedList<>();

		try (final BufferedReader errorReader = Files.newBufferedReader(path, StandardCharsets.US_ASCII))
		{
			String errorRow;

			while ((errorRow = errorReader.readLine()) != null)
			{
				errorClasses.add(errorRow.split(","));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return errorClasses.toArray(new String[0][0]);
	}

	private Double[][] retrieveErrorPredictions(Path path)
	{
		final List<Double[]> errorClasses = new LinkedList<>();

		try (final BufferedReader predictionReader = Files.newBufferedReader(path, StandardCharsets.US_ASCII))
		{
			String predictionRow;

			while ((predictionRow = predictionReader.readLine()) != null)
			{
				final String[] split = predictionRow.split(",");
				final Double[] toDouble = this.stringArrayToDouble(split);

				errorClasses.add(toDouble);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return errorClasses.toArray(new Double[0][0]);
	}

	private IError[][] splitError(IError e)
	{
		final ErrorConfig config = e.getConfig();
		final Mat original = e.originalProperty().get();
		final Mat modified = e.modifiedProperty().get();
		final Mat error = e.errorProperty().get();

		final Mat[][] originalSplit = this.splitImage(original, 15);
		final Mat[][] modifiedSplit = this.splitImage(modified, 15);
		final Mat[][] errorSplit = this.splitImage(error, 15);

		final int cols = originalSplit.length;
		final int rows = originalSplit[0].length;

		final IError[][] errors = new de.thatsich.openfx.errorgeneration.intern.control.entity.Error[cols][rows];

		for (int col = 0; col < originalSplit.length; col++)
		{
			for (int row = 0; row < originalSplit[0].length; row++)
			{
				final Mat originalMat = originalSplit[col][row];
				final Mat modifiedMat = modifiedSplit[col][row];
				final Mat errorMat = errorSplit[col][row];

				errors[col][row] = new Error(config, originalMat, modifiedMat, errorMat);
			}
		}

		return errors;
	}

	private Double[] stringArrayToDouble(String[] strings)
	{
		final Double[] result = new Double[strings.length];

		for (int i = 0; i < strings.length; i++)
		{
			result[i] = Double.parseDouble(strings[i]);
		}

		return result;
	}

	private Mat[][] splitImage(Mat image, int frameSize)
	{
		final Mat[][] result = Images.split(image, frameSize, frameSize);
		this.log.info("Split image.");

		return result;
	}
}
