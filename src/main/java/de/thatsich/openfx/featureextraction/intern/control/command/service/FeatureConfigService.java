package de.thatsich.openfx.featureextraction.intern.control.command.service;

import de.thatsich.core.AConfigurationService;
import javafx.util.Pair;


/**
 * @author Tran Minh Do
 */
public class FeatureConfigService extends AConfigurationService
{
	private static final Pair<String, Integer> TILE_SIZE = new Pair<>("tile_size", 31);
	private static final Pair<String, Integer> EXTRACTOR = new Pair<>("extractor", 0);
	private static final Pair<String, Integer> INDEX = new Pair<>("index", 0);

	public int getLastTileSize()
	{
		return super.get(TILE_SIZE.getKey(), TILE_SIZE.getValue());
	}

	public void setLastTileSize(int lastTileSize)
	{
		super.set(TILE_SIZE.getKey(), lastTileSize);
	}

	public int getLastFeatureExtractorIndex()
	{
		return super.get(EXTRACTOR.getKey(), EXTRACTOR.getValue());
	}

	public void setLastFeatureExtractorIndex(int index)
	{
		super.set(EXTRACTOR.getKey(), index);
	}

	public int getLastFeatureIndex()
	{
		return super.get(INDEX.getKey(), INDEX.getValue());
	}

	public void setLastFeatureIndex(int index)
	{
		super.set(INDEX.getKey(), index);
	}
}
