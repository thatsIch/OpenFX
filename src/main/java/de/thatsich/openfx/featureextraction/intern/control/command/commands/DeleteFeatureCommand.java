package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.intern.control.command.service.FeatureFileStorageService;

public class DeleteFeatureCommand extends ACommand<IFeature>
{
	// Properties
	private final IFeature feature;
	private final FeatureFileStorageService storage;

	@Inject
	public DeleteFeatureCommand(@Assisted IFeature feature, FeatureFileStorageService storage)
	{
		this.feature = feature;
		this.storage = storage;
	}

	@Override
	protected IFeature call() throws Exception
	{
		this.storage.delete(this.feature);

		return this.feature;
	}

}
