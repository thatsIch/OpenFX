package de.thatsich.openfx.imageprocessing.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.imageprocessing.api.control.IImage;
import de.thatsich.openfx.imageprocessing.api.model.IImages;
import de.thatsich.openfx.imageprocessing.intern.control.command.ImageInitCommander;
import de.thatsich.openfx.imageprocessing.intern.control.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.openfx.imageprocessing.intern.control.command.provider.IImageCommandProvider;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ImageListPresenter extends AFXMLPresenter
{
	// Injects
	@Inject private ImageInitCommander init;
	@Inject private IImageCommandProvider provider;
	@Inject private IImages images;

	// Nodes
	@FXML private TableView<IImage> nodeTableViewImageList;
	@FXML private TableColumn<IImage, String> nodeTableColumnImageList;

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	@Override
	protected void initComponents()
	{

	}

	// ================================================== 
	// Binding Implementation 
	// ==================================================
	private void bindTableView()
	{
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}

	private void bindTableViewContent()
	{
		this.nodeTableViewImageList.itemsProperty().bind(this.images.list());
		this.log.info("Bound nodeTableViewImageList to ImageDatabase.");
	}

	private void bindTableViewSelectionModel()
	{
		// change selection > change model
		this.nodeTableViewImageList.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, oldValue, newValue) -> {
			this.images.selected().set(newValue);

			final int index = this.nodeTableViewImageList.getSelectionModel().getSelectedIndex();
			final SetLastImageEntryIndexCommand command = this.provider.createSetLastImageEntryIndexCommand(index);
			command.start();
			this.log.info("Seleced index " + index);
		});
		this.log.info("Bound Model to TableView.");

		// change model > select
		this.images.selected().addListener((observable, oldValue, newValue) -> this.nodeTableViewImageList.getSelectionModel().select(newValue));
		this.log.info("Bound TableView to Model.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnImageList.setCellValueFactory(feature -> new ReadOnlyObjectWrapper<>(feature.getValue().getImageName()));
		this.log.info("Setup CellValueFactory for nodeTableColumnImageList.");
	}


	// ================================================== 
	// Handler Implementation 
	// ==================================================


}
