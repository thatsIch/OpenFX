package de.thatsich.openfx.prediction.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.api.control.IBinaryClassification;
import de.thatsich.openfx.errorgeneration.api.control.IErrorGenerator;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.imageprocessing.api.control.ImageEntry;
import de.thatsich.openfx.prediction.api.control.BinaryPrediction;
import de.thatsich.openfx.prediction.intern.control.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.TestBinaryClassificationCommand;

import java.nio.file.Path;


public interface IPredictionCommandProvider extends ICommandProvider
{
	public TestBinaryClassificationCommand createTestBinaryClassificationCommand(@Assisted Path predictionFolderPath, @Assisted ImageEntry imageEntry, @Assisted int frameSize, @Assisted IErrorGenerator errorGenerator, @Assisted IFeatureExtractor featureExtractor, @Assisted IBinaryClassification binaryClassification);

	public SetLastBinaryPredictionIndexCommand createSetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex);

	public DeleteBinaryPredictionCommand createDeleteBinaryPredictionCommand(BinaryPrediction toBeDeletedBinaryPrediction);
}