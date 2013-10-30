package de.thatsich.bachelor.classification.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.classification.api.core.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.core.IBinaryClassifiers;
import de.thatsich.bachelor.classification.api.core.IClassificationDisplayView;
import de.thatsich.bachelor.classification.api.core.IClassificationInputView;
import de.thatsich.bachelor.classification.api.core.IClassificationListView;
import de.thatsich.bachelor.classification.api.core.IClassificationState;
import de.thatsich.bachelor.classification.intern.command.classifier.RandomForestBinaryClassifier;
import de.thatsich.bachelor.classification.intern.command.classifier.SVMBinaryClassifier;
import de.thatsich.bachelor.classification.intern.model.BinaryClassifications;
import de.thatsich.bachelor.classification.intern.model.BinaryClassifiers;
import de.thatsich.bachelor.classification.intern.model.ClassificationState;
import de.thatsich.bachelor.classification.intern.service.ClassificationConfigService;
import de.thatsich.bachelor.classification.intern.view.ClassificationDisplayView;
import de.thatsich.bachelor.classification.intern.view.ClassificationInputView;
import de.thatsich.bachelor.classification.intern.view.ClassificationListView;
import de.thatsich.core.guice.AWiringModule;


/**
 * Guice Graph of the whole MVP structure
 * 
 * @author Minh
 *
 */
public class TrainWiringModule extends AWiringModule {
	@Override
	protected void bindModule() {
		super.bind(TrainWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindView() {
		super.bind(IClassificationDisplayView.class).to(ClassificationDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IClassificationInputView.class).to(ClassificationInputView.class).in(Scopes.SINGLETON);
		super.bind(IClassificationListView.class).to(ClassificationListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController() {
	}

	@Override
	protected void bindCommand() {
	}

	@Override
	protected void bindModel() {
		super.bind(IBinaryClassifiers.class).to(BinaryClassifiers.class).in(Scopes.SINGLETON);
		super.bind(IBinaryClassifications.class).to(BinaryClassifications.class).in(Scopes.SINGLETON);
		super.bind(IClassificationState.class).to(ClassificationState.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindService() {
		super.bind(ClassificationConfigService.class).in(Scopes.SINGLETON);
		
		super.bind(SVMBinaryClassifier.class).in(Scopes.SINGLETON);
		super.bind(RandomForestBinaryClassifier.class).in(Scopes.SINGLETON);
	}
}
