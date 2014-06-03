package de.thatsich.openfx.errorgeneration.intern.control.command.service;

import de.thatsich.core.AConfigurationService;
import javafx.util.Pair;


/**
 * @author Tran Minh Do
 */
public class ErrorConfigService extends AConfigurationService
{
	private static final Pair<String, Integer> ENTRY_INDEX = new Pair<>("error", 0);
	private static final Pair<String, Integer> GENERATOR_INDEX = new Pair<>("generator", 0);
	private static final Pair<String, Integer> LOOP_COUNT = new Pair<>("count", 1);

	public int getLastErrorEntryIndexInt()
	{
		return super.get(ENTRY_INDEX.getKey(), ENTRY_INDEX.getValue());
	}

	public void setLastErrorEntryIndexInt(int index)
	{
		super.set(ENTRY_INDEX.getKey(), index);
	}

	public int getLastErrorGeneratorIndexInt()
	{
		return super.get(GENERATOR_INDEX.getKey(), GENERATOR_INDEX.getValue());
	}

	public void setLastErrorGeneratorIndexInt(int index)
	{
		super.set(GENERATOR_INDEX.getKey(), index);
	}

	public int getLastErrorCountInt()
	{
		return super.get(LOOP_COUNT.getKey(), LOOP_COUNT.getValue());
	}

	public void setLastErrorCountInt(int lastErrorCount)
	{
		super.set(LOOP_COUNT.getKey(), lastErrorCount);
	}
}
