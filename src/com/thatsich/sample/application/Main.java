package com.thatsich.sample.application;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;

import org.opencv.core.Core;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.thatsich.sample.application.guice.SampleModule;
import com.thatsich.sample.javafx.view.IDisplayView;


/**
 * Main Execution Class
 * @author Tran Minh Do
 */
//TODO Error Generator
public class Main extends GuiceApplication {
	
	/**
	 * First instantiated view.
	 */
	@Inject
	private IDisplayView view;

	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
//		Mat[][] split = ImageSplitter.split(m, 2, 2);
        
        Main.launch(args);
    }

    
	/*
	 * ================================================== 
	 * GuiceApplication Implementation
	 * ==================================================
	 */
	/**
	 * Initialize the Application with the first scene on stage.
	 * Called automatically by Super Class.
	 * @param primaryStage Main Stage to be drawn on
	 */
    @Override public void start(Stage primaryStage) throws IOException {

		if (this.view == null) throw new IllegalStateException("View not instantiated."); 
		
		final Parent root = this.view.getRoot();
		if (root == null) throw new IllegalStateException("Root damaged.");
		
		final URL url = this.getClass().getResource("opencv.png");
		if (url == null) throw new IllegalStateException("Icon not found."); 
		final Image icon = new Image(url.toString());
		if (icon.isError()) throw new IllegalStateException();
		
		 // Put the loaded user interface onto the primary stage.
		Scene scene = SceneBuilder
			.create()
			.root(root)
			.build();
		
        StageBuilder.create()
        	.title("Sample")
        	.icons(icon)
        	.scene(scene)
        	.resizable(false)
            .applyTo(primaryStage);

        // Show the primary stage
        primaryStage.show();
	}
	
	/**
	 * Initialize all added modules
	 * Automatically called by super class
	 * @param modules Empty Module-List 
	 */
	@Override public void init(List<Module> modules) {
		modules.add(new SampleModule());
	}
}