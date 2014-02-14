package de.thatsich.bachelor;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.cathive.fx.guice.GuiceApplication;
import com.google.inject.Inject;
import com.google.inject.Module;

import de.thatsich.bachelor.taboverview.api.core.ITabOverviewView;
import de.thatsich.bachelor.taboverview.api.guice.TabOverviewCommandModule;
import de.thatsich.bachelor.taboverview.api.guice.TabOverviewWiringModule;
import de.thatsich.core.guice.LoggerModule;
import de.thatsich.core.guice.PostInitModule;
import de.thatsich.core.opencv.OpenCVLoader;


// TODO need Threshold
// ODO low priority - Random Feature Extractor
// TODO CNBC
// TODO Denoising http://docs.opencv.org/trunk/modules/photo/doc/denoising.html
// TODO CvtColor, CvSmooth, CvThreshold
// TODO private static double sigmoid(double x) { return 1 / (1 + Math.exp(-x));
// Converters.Mat_to_vector_float( positiveTrainData, null );
/**
 * Main Execution Class
 * 
 * @author Tran Minh Do
 */
public class Main extends GuiceApplication
{
	/**
	 * First instantiated view.
	 */
	@Inject
	private ITabOverviewView	view;

	/**
	 * 
	 * @param args
	 */
	public static void main( String[] args )
	{
		// Loading OpenCV Bindings
		OpenCVLoader.loadLibrary();

		Main.launch( args );
	}

	/*
	 * ==================================================
	 * GuiceApplication Implementation
	 * ==================================================
	 */
	/**
	 * Initialize the Application with the first scene on stage.
	 * Called automatically by Super Class.
	 * 
	 * @param primaryStage
	 *            Main Stage to be drawn on
	 */
	@Override
	public void start( Stage primaryStage ) throws IOException
	{
		if ( this.view == null ) throw new NullPointerException( "View not instantiated." );

		final Parent root = this.view.getRoot();
		if ( root == null ) throw new NullPointerException( "Root damaged." );

		final URL url = this.getClass().getResource( "opencv.png" );
		if ( url == null ) throw new NullPointerException( "Icon not found." );
		final Image icon = new Image( url.toString() );
		if ( icon.isError() ) throw new IllegalStateException( "Icon could not be loaded" );

		// Put the loaded user interface onto the primary stage.
		final Scene scene = new Scene( root );

		// Show the primary stage
		primaryStage.setTitle( "Sample" );
		primaryStage.getIcons().add( icon );
		primaryStage.setScene( scene );
		primaryStage.setMaximized( true );
		primaryStage.show();
	}

	/**
	 * Initialize all added modules
	 * Automatically called by super class
	 * 
	 * @param modules
	 *            Empty Module-List
	 */
	@Override
	public void init( List<Module> modules )
	{
		modules.add( new LoggerModule() );
		modules.add( new PostInitModule() );

		modules.add( new TabOverviewWiringModule() );
		modules.add( new TabOverviewCommandModule() );
	}
}