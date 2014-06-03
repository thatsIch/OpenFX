/**
 *
 */
package de.thatsich.openfx.imageprocessing.intern.control.command.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import de.thatsich.openfx.imageprocessing.intern.control.command.service.ImageConfigService;
import de.thatsich.core.javafx.ACommand;

/**
 * Saves a String as LastLocation
 *
 * @author thatsIch
 */
public class SetLastLocationCommand extends ACommand<Void>
{

	/**
	 * to be saved Last Location
	 */
	private final String lastLocation;

	/**
	 * Injected Config to retrieve last location.
	 */
	@Inject
	private ImageConfigService config;

	@Inject
	private SetLastLocationCommand(@Assisted String lastLocation)
	{
		this.lastLocation = lastLocation;
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
