package de.thatsich.openfx.featureextraction.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.featureextraction.intern.control.entity.FeatureVectorSet;

import java.io.IOException;
import java.nio.file.Files;

public class DeleteFeatureVectorSetCommand extends ACommand<FeatureVectorSet>
{
	// Properties
	private final FeatureVectorSet featureVectorSet;

	@Inject
	public DeleteFeatureVectorSetCommand(@Assisted FeatureVectorSet featureVectorSet)
	{
		this.featureVectorSet = featureVectorSet;
	}

	@Override
	protected FeatureVectorSet call() throws Exception
	{
		try
		{
			this.log.info(this.featureVectorSet.path().get().toString());
			Files.delete(this.featureVectorSet.path().get());

			this.log.info("Deleted FeatureVectorSet from FileSystem.");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			this.log.info("Exception from deleting FeatureVectorSet from FileSystem.");
		}

		return this.featureVectorSet;
	}

}
