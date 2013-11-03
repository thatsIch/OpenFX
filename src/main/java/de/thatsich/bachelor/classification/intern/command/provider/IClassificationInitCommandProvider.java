package de.thatsich.bachelor.classification.intern.command.provider;

import java.nio.file.Path;

import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.GetLastBinaryClassifierIndexCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassificationListCommand;
import de.thatsich.bachelor.classification.intern.command.commands.InitBinaryClassifierListCommand;
import de.thatsich.core.guice.ICommandProvider;

public interface IClassificationInitCommandProvider extends ICommandProvider {
	public InitBinaryClassifierListCommand createInitBinaryClassifierListCommand();
	public InitBinaryClassificationListCommand createInitBinaryClassificationListCommand(Path trainedBinaryClassifierFolderPath);
	
	public GetLastBinaryClassifierIndexCommand createGetLastBinaryClassifierIndexCommand();
	public GetLastBinaryClassificationIndexCommand createGetLastBinaryClassificationIndexCommand();
}
