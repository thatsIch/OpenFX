package de.thatsich.bachelor.classificationtesting.restricted.app.guice;

import java.nio.file.Path;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.GetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.InitBinaryPredictionListCommand;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.InitPredictionFolderCommand;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.SetLastBinaryPredictionIndexCommand;
import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.TestBinaryClassificationCommand;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.restricted.command.extractor.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;


public interface BinaryPredictionCommandProvider {
	public TestBinaryClassificationCommand createTestBinaryClassificationCommand(@Assisted Path predictionFolderPath, @Assisted ImageEntry imageEntry, @Assisted int frameSize, @Assisted IErrorGenerator errorGenerator, @Assisted IFeatureExtractor featureExtractor, @Assisted IBinaryClassification binaryClassification);
	public InitPredictionFolderCommand createInitPredictionFolderCommand(@Assisted Path predictionFolderPath);
	public SetLastBinaryPredictionIndexCommand createSetLastBinaryPredictionIndexCommand(@Assisted int lastBinaryPredictionIndex);
	public InitBinaryPredictionListCommand createInitBinaryPredictionListCommand(@Assisted Path predictionFolderPath);
	public GetLastBinaryPredictionIndexCommand createGetLastBinaryPredictionIndexCommand();
}
