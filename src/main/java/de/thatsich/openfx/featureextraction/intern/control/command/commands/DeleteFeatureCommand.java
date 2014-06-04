package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;

import java.io.IOException;
import java.nio.file.Files;

public class DeleteFeatureCommand extends ACommand<IFeature>
{
	// Properties
	private final IFeature feature;

	@Inject
	public DeleteFeatureCommand(@Assisted IFeature featureVectorSet)
	{
		this.feature = featureVectorSet;
	}

	@Override
	protected IFeature call() throws Exception
	{
		try
		{
			this.log.info(this.feature.getPath().toString());
			Files.delete(this.feature.getPath());

			this.log.info("Deleted FeatureVectorSet from FileSystem.");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.log.info("Exception from deleting FeatureVectorSet from FileSystem.");
		}

		return this.feature;
	}

}
