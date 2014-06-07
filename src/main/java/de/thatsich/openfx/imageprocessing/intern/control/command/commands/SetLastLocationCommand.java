/**
 *
 */
package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.core.javafx.ACommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;

/**
 * Saves a String as LastLocation
 *
 * @author thatsIch
 */
public class SetLastLocationCommand extends ACommand<Void>
{
	private final String lastLocation;
	private final ImageConfigService config;

	@Inject
	private SetLastLocationCommand(@Assisted String lastLocation, ImageConfigService config)
	{
		this.lastLocation = lastLocation;
		this.config = config;
	}

	/**
	 * sets the last location in the config;
	 */
	@Override
	protected Void call() throws Exception
	{
		this.config.setLastLocationString(this.lastLocation);

		return null;
	}

}
