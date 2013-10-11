package de.thatsich.bachelor.javafx.presentation.feature;

import java.net.URL;
import java.util.ResourceBundle;

import com.google.inject.Inject;

import de.thatsich.bachelor.javafx.business.model.ImageDatabase;
import de.thatsich.core.javafx.AFXMLPresenter;

public class FeatureListPresenter extends AFXMLPresenter {

	// Nodes
//	@FXML TableView<ImageEntry> nodeTableViewImageList;
//	@FXML TableColumn<ImageEntry, String> nodeTableColumnImageList;
	
	// Injects
	@Inject private ImageDatabase images;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		this.nodeTableViewImageList.itemsProperty().bind(this.images.getImageEntriesProperty());
//		this.nodeTableColumnImageList.setCellValueFactory(new Callback<CellDataFeatures<ImageEntry, String>, ObservableValue<String>>() {
//			@Override public ObservableValue<String> call(CellDataFeatures<ImageEntry, String> feature) {
//				return new ReadOnlyObjectWrapper<String>(feature.getValue().getName());
//			}
//		});
//		
//		this.nodeTableViewImageList.getSelectionModel().selectFirst();
//		this.nodeTableViewImageList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ImageEntry>() {
//			@Override public void changed(ObservableValue<? extends ImageEntry> paramObservableValue, ImageEntry oldValue, ImageEntry newValue) {
//				images.getImageEntryProperty().set(newValue);
//			}
//		});
	}

}
