package de.thatsich.core;

import javafx.util.StringConverter;
import de.thatsich.core.opencv.IMetric;

public class StringMetricConverter extends StringConverter<IMetric> {

	@Override
	public IMetric fromString(String string) {
		return null;
	}

	@Override
	public String toString(IMetric metric) {
		return metric.getName();
	}

}
