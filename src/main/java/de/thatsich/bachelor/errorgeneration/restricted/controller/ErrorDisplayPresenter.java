package de.thatsich.bachelor.errorgeneration.restricted.controller;

import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.google.inject.Inject;

import de.thatsich.bachelor.errorgeneration.api.core.IErrorEntries;
import de.thatsich.bachelor.errorgeneration.api.entities.ErrorEntry;
import de.thatsich.bachelor.errorgeneration.restricted.command.ErrorInitCommander;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.core.opencv.Images;


public class ErrorDisplayPresenter extends AFXMLPresenter
{

	// Nodes
	@FXML
	private ImageView		nodeImageViewError;

	// Injects
	@Inject
	private IErrorEntries	errorEntryList;

	@Inject
	ErrorInitCommander		initCommander;

	@Override
	protected void initComponents()
	{
		final ErrorEntry entry = this.errorEntryList.getSelectedErrorEntry();
		if ( entry != null )
		{
			final Image image = this.errorEntryToImage( entry );
			this.nodeImageViewError.imageProperty().setValue( image );
			this.log.info( "Initialized nodeImageViewError." );
		}
	}

	@Override
	protected void bindComponents()
	{
		this.errorEntryList.getSelectedErrorEntryProperty().addListener( new ChangeListener<ErrorEntry>()
		{
			@Override
			public void changed( ObservableValue<? extends ErrorEntry> observable, ErrorEntry oldValue, ErrorEntry newValue )
			{
				if ( newValue != null )
				{
					final Image image = errorEntryToImage( newValue );
					nodeImageViewError.imageProperty().setValue( image );
					log.info( "Selected new ErrorEntry." );
				}
				else
				{
					nodeImageViewError.imageProperty().set( null );
					log.info( "Set Image to null." );
				}
			}
		} );
		this.log.info( "Bound nodeImageViewError to Model." );
	}

	private Image errorEntryToImage( ErrorEntry entry )
	{
		final List<Mat> listMat = Arrays.asList( entry.getOriginalMat(), entry.getOriginalWithErrorMat(), entry.getErrorMat() );
		Mat mergedMat = new Mat( entry.getOriginalMat().size(), CvType.CV_8UC3 );
		Core.merge( listMat, mergedMat );

		return Images.toImage( mergedMat );
	}
}
