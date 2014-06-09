package de.thatsich.openfx.network.intern.control.entity;

import de.thatsich.openfx.network.api.control.entity.INetwork;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.nio.file.Path;

/**
 * @author thatsIch
 * @since 01.06.2014.
 */
public class Network implements INetwork
{
	private final ReadOnlyObjectWrapper<Path> filePath = new ReadOnlyObjectWrapper<>();

	public Network(Path filePath)
	{
		this.filePath.set(filePath);
	}

	public ReadOnlyObjectProperty<Path> getFilePathProperty()
	{
		return this.filePath.getReadOnlyProperty();
	}
}
