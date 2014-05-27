package de.thatsich.bachelor.classification.api.guice;

import com.google.inject.Scopes;

import de.thatsich.bachelor.classification.api.models.IBinaryClassifications;
import de.thatsich.bachelor.classification.api.models.IBinaryClassifiers;
import de.thatsich.bachelor.classification.api.models.IClassificationState;
import de.thatsich.bachelor.classification.api.views.IClassificationDisplayView;
import de.thatsich.bachelor.classification.api.views.IClassificationInputView;
import de.thatsich.bachelor.classification.api.views.IClassificationListView;
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


public class ClassificationWiringModule extends AWiringModule
{
	@Override
	protected void bindModule()
	{
		super.bind( ClassificationWiringModule.class ).toInstance( this );
	}

	@Override
	protected void bindView()
	{
		super.bind( IClassificationDisplayView.class ).to( ClassificationDisplayView.class ).in( Scopes.SINGLETON );
		super.bind( IClassificationInputView.class ).to( ClassificationInputView.class ).in( Scopes.SINGLETON );
		super.bind( IClassificationListView.class ).to( ClassificationListView.class ).in( Scopes.SINGLETON );
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
		super.bind( IBinaryClassifiers.class ).to( BinaryClassifiers.class ).in( Scopes.SINGLETON );
		super.bind( IBinaryClassifications.class ).to( BinaryClassifications.class ).in( Scopes.SINGLETON );
		super.bind( IClassificationState.class ).to( ClassificationState.class ).in( Scopes.SINGLETON );
	}

	@Override
	protected void bindService()
	{
		super.bind( ClassificationConfigService.class ).in( Scopes.SINGLETON );
		super.bind( SVMBinaryClassifier.class ).in( Scopes.SINGLETON );
		super.bind( RandomForestBinaryClassifier.class ).in( Scopes.SINGLETON );
	}
}
