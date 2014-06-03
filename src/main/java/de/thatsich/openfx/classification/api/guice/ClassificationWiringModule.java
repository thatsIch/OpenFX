package de.thatsich.openfx.classification.api.guice;

import com.google.inject.Scopes;
import de.thatsich.openfx.classification.api.model.IBinaryClassifications;
import de.thatsich.openfx.classification.api.model.IBinaryClassifiers;
import de.thatsich.openfx.classification.api.model.IClassificationState;
import de.thatsich.openfx.classification.api.view.IClassificationDisplayView;
import de.thatsich.openfx.classification.api.view.IClassificationInputView;
import de.thatsich.openfx.classification.api.view.IClassificationListView;
import de.thatsich.openfx.classification.intern.control.classifier.RandomForestBinaryClassifier;
import de.thatsich.openfx.classification.intern.control.classifier.SVMBinaryClassifier;
import de.thatsich.openfx.classification.intern.model.BinaryClassifications;
import de.thatsich.openfx.classification.intern.model.BinaryClassifiers;
import de.thatsich.openfx.classification.intern.model.ClassificationState;
import de.thatsich.openfx.classification.intern.control.command.service.ClassificationConfigService;
import de.thatsich.openfx.classification.intern.view.ClassificationDisplayView;
import de.thatsich.openfx.classification.intern.view.ClassificationInputView;
import de.thatsich.openfx.classification.intern.view.ClassificationListView;
import de.thatsich.core.guice.AWiringModule;


public class ClassificationWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind(ClassificationWiringModule.class).toInstance(this);
	}

	@Override
	protected void bindService()
	{
		super.bind(ClassificationConfigService.class).in(Scopes.SINGLETON);
		super.bind(SVMBinaryClassifier.class).in(Scopes.SINGLETON);
		super.bind(RandomForestBinaryClassifier.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindView()
	{
		super.bind(IClassificationDisplayView.class).to(ClassificationDisplayView.class).in(Scopes.SINGLETON);
		super.bind(IClassificationInputView.class).to(ClassificationInputView.class).in(Scopes.SINGLETON);
		super.bind(IClassificationListView.class).to(ClassificationListView.class).in(Scopes.SINGLETON);
	}

	@Override
	protected void bindController()
	{
	}

	@Override
	protected void bindCommand()
	{
	}

	@Override
	protected void bindModel()
	{
		super.bind(IBinaryClassifiers.class).to(BinaryClassifiers.class).in(Scopes.SINGLETON);
		super.bind(IBinaryClassifications.class).to(BinaryClassifications.class).in(Scopes.SINGLETON);
		super.bind(IClassificationState.class).to(ClassificationState.class).in(Scopes.SINGLETON);
	}
}
