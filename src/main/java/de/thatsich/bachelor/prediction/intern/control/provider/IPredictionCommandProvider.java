package de.thatsich.bachelor.prediction.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.intern.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.prediction.api.entities.BinaryPrediction;
import de.thatsich.bachelor.prediction.intern.control.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.bachelor.prediction.intern.control.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.prediction.intern.control.command.commands.TestBinaryClassificationCommand;
import de.thatsich.core.guice.ICommandProvider;

import java.nio.file.Path;


public interface IPredictionCommandProvider extends ICommandProvider
{
	public TestBinaryClassificationCommand createTestBinaryClassificationCommand(@Assisted Path predictionFolderPath, @Assisted ImageEntry imageEntry, @Assisted int frameSize, @Assisted IErrorGenerator errorGenerator, @Assisted IFeatureExtractor featureExtractor, @Assisted IBinaryClassification binaryClassification);

	public SetLastBinaryPredictionIndexCommand createSetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex);

	public DeleteBinaryPredictionCommand createDeleteBinaryPredictionCommand(BinaryPrediction toBeDeletedBinaryPrediction);
}
