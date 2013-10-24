package de.thatsich.bachelor.classificationtesting.restricted.app.guice;

import com.google.inject.assistedinject.Assisted;

import de.thatsich.bachelor.classificationtesting.restricted.controller.commands.TestBinaryClassificationCommand;
import de.thatsich.bachelor.classificationtraining.api.entities.IBinaryClassification;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.featureextraction.api.core.IFeatureExtractor;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;


public interface TestCommandProvider {
	public TestBinaryClassificationCommand createTestBinaryClassificationCommand(@Assisted ImageEntry imageEntry, @Assisted int frameSize, @Assisted IErrorGenerator errorGenerator, @Assisted IFeatureExtractor featureExtractor, @Assisted IBinaryClassification binaryClassification);
}
