package de.thatsich.bachelor.errorgeneration.restricted.services;

import java.nio.file.Path;

import org.opencv.core.Mat;

import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.api.entities.IErrorGenerator;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.ApplyErrorCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.CreateErrorImageCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.DeleteErrorEntryCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.GetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.GetLastErrorGeneratorIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.InitErrorEntryListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.InitErrorGeneratorListCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.SetLastErrorCountCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.SetLastErrorEntryIndexCommand;
import de.thatsich.bachelor.errorgeneration.restricted.controller.commands.SetLastErrorGeneratorIndexCommand;

public interface ErrorCommandService {
	public InitErrorEntryListCommand createInitErrorEntryListCommand(Path errorInputFolderPath);
	public InitErrorGeneratorListCommand createInitErrorGeneratorListCommand(); 
	
	public GetLastErrorGeneratorIndexCommand createGetLastErrorGeneratorIndexCommand();
	public GetLastErrorEntryIndexCommand createGetLastErrorEntryIndexCommand();
	
	public SetLastErrorEntryIndexCommand createSetLastErrorEntryIndexCommand(int lastErrorEntryIndex);
	public SetLastErrorGeneratorIndexCommand createSetLastErrorGeneratorIndexCommand(int lastErrorGeneratorIndex);
	public SetLastErrorCountCommand createSetLastErrorCountCommand(int lastErrorLoopCount);
	
	public ApplyErrorCommand createApplyErrorCommand(Mat imageMat, Path imagePath, IErrorGenerator generator);
	public DeleteErrorEntryCommand createDeleteErrorEntryCommand(ErrorEntry entry);
	public CreateErrorImageCommand createCreateErrorImageCommand(ErrorEntry entry);
}
