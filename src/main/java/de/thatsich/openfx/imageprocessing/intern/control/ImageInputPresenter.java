package de.thatsich.openfx.imageprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.javafx.CommandExecutor;
import de.thatsich.openfx.imageprocessing.api.control.entity.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImageState;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import de.thatsich.openfx.imageprocessing.intern.control.command.ImageInitCommander;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.CreateImageCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.DeleteImageCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.provider.IImageCommandProvider;
import de.thatsich.openfx.imageprocessing.intern.control.handler.CreateImageSucceededHandler;
import de.thatsich.openfx.imageprocessing.intern.control.handler.DeleteImageEntrySucceededHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Presenter
 *
 * Represents the input controls for the image database part.
 * Offers basic ability to modify the underlying image-database.
 *
 * @author Tran Minh Do
 */
public class ImageInputPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ImageInitCommander init;
	@Inject private IImageCommandProvider provider;
	@Inject private IImages images;
	@Inject private IImageState state;
	@Inject private ImageFileChooser chooser;

	// Nodes
	@FXML private Button nodeButtonRemoveImage;
	@FXML private Button nodeButtonResetDatabase;

	// Binding Implementation
	@Override
	protected void bindComponents()
	{
		this.bindButtons();
	}

	// Initialization Implementation
	@Override
	protected void initComponents()
	{
	}

	private void bindButtons()
	{
		this.nodeButtonRemoveImage.disableProperty().bind(this.images.selected().isNull());
		this.nodeButtonResetDatabase.disableProperty().bind(this.images.list().emptyProperty());
	}

	// ================================================== 
	// GUI Implementation 
	// ==================================================

	/**
	 * Shows a FileChooser and
	 * adds selected image to model
	 *
	 * @throws IOException
	 */
	@FXML
	private void onAddImageAction() throws IOException
	{

		final List<Path> imagePathList = this.chooser.show();
		if (imagePathList == null)
		{
			return;
		}
		this.log.info("Fetched Path from chosen Image.");

		final ExecutorService executor = CommandExecutor.newFixedThreadPool(imagePathList.size());
		this.log.info("Created Executor.");

		for (final Path imagePath : imagePathList)
		{
			final CreateImageCommand command = this.provider.createCreateImageCommand(imagePath);
			command.setOnSucceededCommandHandler(CreateImageSucceededHandler.class);
			command.setExecutor(executor);
			command.start();
			this.log.info("File copied and inserted into EntryList.");
		}

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}

	/**
	 * Removes the currently selected image
	 *
	 * @throws IOException
	 */
	@FXML
	private void onRemoveImageAction() throws IOException
	{
		final IImage choice = this.images.selected().get();
		this.log.info("Fetched selected ImageEntry.");

		if (choice == null)
		{
			this.log.info("Selection was empty. Deleting nothing.");
			return;
		}

		final DeleteImageCommand command = this.provider.createDeleteImageCommand(choice);
		command.setOnSucceededCommandHandler(DeleteImageEntrySucceededHandler.class);
		command.start();
		this.log.info("File deleted and removed from EntryList.");
	}

	/**
	 * Resets the image data base
	 *
	 * @throws IOException
	 */
	@FXML
	private void onResetDatabaseAction() throws IOException
	{
		final List<IImage> list = this.images.list();
		final ExecutorService executor = CommandExecutor.newFixedThreadPool(list.size());
		this.log.info("Initialized Executor for resetting all Errors.");

		for (IImage entry : list)
		{
			final DeleteImageCommand command = this.provider.createDeleteImageCommand(entry);
			command.setOnSucceededCommandHandler(DeleteImageEntrySucceededHandler.class);
			command.setExecutor(executor);
			command.start();
		}
		this.log.info("EntryList resetted.");

		executor.execute(System::gc);
		this.log.info("Running Garbage Collector.");

		executor.shutdown();
		this.log.info("Shutting down Executor.");
	}
}
