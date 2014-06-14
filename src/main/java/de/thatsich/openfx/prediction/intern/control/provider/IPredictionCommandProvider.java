package de.thatsich.openfx.prediction.intern.control.provider;

import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.api.control.entity.ITrainedBinaryClassifier;
import de.thatsich.openfx.errorgeneration.api.control.entity.IErrorGenerator;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureExtractor;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.prediction.api.control.entity.IBinaryPrediction;
import de.thatsich.openfx.prediction.intern.control.command.commands.DeleteBinaryPredictionCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.openfx.prediction.intern.control.command.commands.TestBinaryClassificationCommand;


public interface IPredictionCommandProvider extends ICommandProvider
{
	public TestBinaryClassificationCommand createTestBinaryClassificationCommand(@Assisted IImage imageEntry, @Assisted int frameSize, @Assisted IErrorGenerator errorGenerator, @Assisted IFeatureExtractor featureExtractor, @Assisted ITrainedBinaryClassifier binaryClassification);

	public SetLastBinaryPredictionIndexCommand createSetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex);

	public DeleteBinaryPredictionCommand createDeleteBinaryPredictionCommand(IBinaryPrediction toBeDeletedBinaryPrediction);
}
