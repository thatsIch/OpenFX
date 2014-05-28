package de.thatsich.bachelor.imageprocessing.intern.controller;

import com.google.inject.Inject;
import de.thatsich.bachelor.imageprocessing.api.core.IImageEntries;
import de.thatsich.bachelor.imageprocessing.api.entities.ImageEntry;
import de.thatsich.bachelor.imageprocessing.intern.command.ImageInitCommander;
import de.thatsich.bachelor.imageprocessing.intern.command.commands.SetLastImageEntryIndexCommand;
import de.thatsich.bachelor.imageprocessing.intern.command.provider.IImageCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ImageListPresenter extends AFXMLPresenter
{

	// Nodes
	@FXML TableView<ImageEntry> nodeTableViewImageList;
	@FXML TableColumn<ImageEntry, String> nodeTableColumnImageList;
	@Inject ImageInitCommander initCommander;
	// Injects
	@Inject
	private IImageCommandProvider commander;
	@Inject
	private IImageEntries imageEntries;

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
		this.nodeTableViewImageList.itemsProperty().bind(this.imageEntries.imageEntriesmageEntryListProperty());
		this.log.info("Bound nodeTableViewImageList to ImageDatabase.");
	}

	private void bindTableViewSelectionModel()
	{
		// change selection > change model
		this.nodeTableViewImageList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageEntry>()
		{
			@Override
			public void changed(ObservableValue<? extends ImageEntry> paramObservableValue, ImageEntry oldValue, ImageEntry newValue)
			{
				imageEntries.selectedImageEntryProperty().set(newValue);

				final int index = nodeTableViewImageList.getSelectionModel().getSelectedIndex();
				final SetLastImageEntryIndexCommand command = commander.createSetLastImageEntryIndexCommand(index);
				command.start();
				log.info("Seleced index " + index);
			}
		});
		this.log.info("Bound Model to TableView.");

		// change model > select
		this.imageEntries.selectedImageEntryProperty().addListener(new ChangeListener<ImageEntry>()
		{
			@Override
			public void changed(ObservableValue<? extends ImageEntry> observable, ImageEntry oldValue, ImageEntry newValue)
			{
				nodeTableViewImageList.getSelectionModel().select(newValue);
			}
		});
		this.log.info("Bound TableView to Model.");
	}

	private void bindTableViewCellValue()
	{
		this.nodeTableColumnImageList.setCellValueFactory(new Callback<CellDataFeatures<ImageEntry, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<ImageEntry, String> feature)
			{
				return new ReadOnlyObjectWrapper<>(feature.getValue().getName());
			}
		});
		this.log.info("Setup CellValueFactory for nodeTableColumnImageList.");
	}


	// ================================================== 
	// Handler Implementation 
	// ==================================================


}
