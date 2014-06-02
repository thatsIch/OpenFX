package de.thatsich.bachelor.featureextraction.intern.services;

import de.thatsich.core.AConfigurationService;
import javafx.util.Pair;


/**
 * @author Tran Minh Do
 */
public class FeatureConfigService extends AConfigurationService
{
	private static final Pair<String, Integer> FRAME_SIZE = new Pair<>("frame_size", 2);
	private static final Pair<String, Integer> EXTRACTOR = new Pair<>("extractor", 0);
	private static final Pair<String, Integer> INDEX = new Pair<>("index", 0);

	public int getLastFrameSizeInt()
	{
		return super.get(FRAME_SIZE.getKey(), FRAME_SIZE.getValue());
	}

	public void setLastFrameSizeInt(int lastFrameSize)
	{
		super.set(FRAME_SIZE.getKey(), lastFrameSize);
	}

	public int getLastFeatureExtractorIndexInt()
	{
		return super.get(EXTRACTOR.getKey(), EXTRACTOR.getValue());
	}

	public void setLastFeatureExtractorIndex(int index)
	{
		super.set(EXTRACTOR.getKey(), index);
	}

	public int getLastFeatureVectorIndexInt()
	{
		return super.get(INDEX.getKey(), INDEX.getValue());
	}

	public void setLastFeatureVectorIndexInt(int index)
	{
		super.set(INDEX.getKey(), index);
	}
}
