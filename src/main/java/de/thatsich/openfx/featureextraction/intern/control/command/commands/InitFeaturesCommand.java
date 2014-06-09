package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureFileStorageService;

import java.util.List;

public class InitFeaturesCommand extends ACommand<List<IFeature>>
{
	private final FeatureFileStorageService storage;

	@Inject
	protected InitFeaturesCommand(FeatureFileStorageService storage)
	{
		this.storage = storage;
	}

	@Override
	protected List<IFeature> call() throws Exception
	{
		return this.storage.init();
	}
}
