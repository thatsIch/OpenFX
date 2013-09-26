package de.thatsich.core.opencv;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ImageShow {

	public void show(Image image) {
		final ImageView view = new ImageView();
		final Pane layout = new HBox();
		final Scene scene = new Scene(layout);
		final Stage stage = new Stage();
		
		view.setImage(image);
		layout.getChildren().add(view);
		stage.setScene(scene);
		
		stage.show();
	}
}
