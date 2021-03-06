package de.thatsich.openfx.classification.intern.control.command.service;

import com.google.inject.Inject;
import de.thatsich.core.AFileStorageService;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.classification.api.model.IClassificationState;
import de.thatsich.openfx.classification.intern.control.classification.SVMTrainedBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.core.BinaryClassificationConfig;
import de.thatsich.openfx.classification.intern.control.provider.IClassificationCommandProvider;
import org.opencv.ml.CvRTrees;
import org.opencv.ml.CvSVM;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

/**
 * @author thatsIch
 * @since 07.06.2014.
 */
public class ClassificationFileStorageService extends AFileStorageService<ITrainedBinaryClassifier>
{
	private static final String SVM_EXT = ".svm";
	private static final String RF_EXT = ".rf";

	private final IClassificationCommandProvider provider;

	@Inject
	protected ClassificationFileStorageService(IClassificationState state, IClassificationCommandProvider provider)
	{
		super(state.path().get());
		this.provider = provider;
	}

	@Override
	public List<ITrainedBinaryClassifier> init() throws IOException
	{
		final List<ITrainedBinaryClassifier> classifications = new LinkedList<>();

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.storagePath))
		{
			for (Path child : stream)
			{
				final ITrainedBinaryClassifier bc = this.retrieve(child);
				classifications.add(bc);
			}
		}
		catch (IOException | DirectoryIteratorException e)
		{
			e.printStackTrace();
		}
		this.log.info("All BinaryClassification added.");

		return classifications;
	}

	@Override
	public ITrainedBinaryClassifier create(ITrainedBinaryClassifier elem) throws IOException
	{
		final String fileName = elem.getConfig().toString();
		final String withExtension;
		if (elem instanceof SVMTrainedBinaryClassifier)
		{
			withExtension = fileName + SVM_EXT;
		}
		else
		{
			withExtension = fileName + RF_EXT;
		}

		final Path filePath = this.storagePath.resolve(withExtension);
		final String fullStringPath = filePath.toAbsolutePath().toString();

		try
		{
			elem.save(fullStringPath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return elem;
	}

	@Override
	public ITrainedBinaryClassifier retrieve(Path path) throws IOException
	{
		final Path filePath = path.getFileName();
		final String fileName = filePath.toString();
		final String fullStringPath = path.toAbsolutePath().toString();

		final String withoutExt = this.getFileNameWithoutExtension(path);
		final BinaryClassificationConfig config = new BinaryClassificationConfig(withoutExt);

		final ITrainedBinaryClassifier bc;
		if (fileName.endsWith(SVM_EXT))
		{
			bc = this.provider.createSVMTraindBinaryClassifier(new CvSVM(), config);
		}
		else
		{
			bc = this.provider.createRandomForestTraindBinaryClassifier(new CvRTrees(), config);
		}
		this.log.info("Resolved BinaryClassification " + bc);

		try
		{
			bc.load(fullStringPath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return bc;
	}

	@Override
	public ITrainedBinaryClassifier update(ITrainedBinaryClassifier elem) throws IOException
	{
		return elem;
	}

	@Override
	public void delete(ITrainedBinaryClassifier elem) throws IOException
	{
		final String fileName;
		if (elem instanceof SVMTrainedBinaryClassifier)
		{
			fileName = elem.getConfig().toString() + SVM_EXT;
		}
		else
		{
			fileName = elem.getConfig().toString() + RF_EXT;
		}

		final Path filePath = super.storagePath.resolve(fileName);
		if (Files.exists(filePath))
		{
			Files.delete(filePath);
			this.log.info("Deleted " + filePath);
		}
	}
}
