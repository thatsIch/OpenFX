package de.thatsich.openfx.classification.intern.control.provider;

import de.thatsich.core.guice.ICommandProvider;
import de.thatsich.openfx.classification.intern.control.command.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.InitBinaryClassificationsCommand;
import de.thatsich.openfx.classification.intern.control.command.commands.InitBinaryClassifiersCommand;

public interface IClassificationInitCommandProvider extends ICommandProvider
{
	public InitBinaryClassifiersCommand createInitBinaryClassifierListCommand();

	public InitBinaryClassificationsCommand createInitBinaryClassificationListCommand();

	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();

	public GetLastBinaryClassificationIndexCommand createGetLastBinaryClassificationIndexCommand();
}
