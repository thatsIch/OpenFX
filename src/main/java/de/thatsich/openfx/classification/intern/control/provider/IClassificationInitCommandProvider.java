package de.thatsich.openfx.classification.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.intern.control.command.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.InitBinaryClassificationsCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.InitBinaryClassifiersCommand;

import java.nio.file.Path;

public interface IClassificationInitCommandProvider extends ICommandProvider
{
	public InitBinaryClassifiersCommand createInitBinaryClassifierListCommand();

	public InitBinaryClassificationsCommand createInitBinaryClassificationListCommand(Path trainedBinaryClassifierFolderPath);

	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();

	public GetLastBinaryClassificationIndexCommand createGetLastBinaryClassificationIndexCommand();
}
