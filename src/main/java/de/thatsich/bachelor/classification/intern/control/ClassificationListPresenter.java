package de.thatsich.bachelor.classification.intern.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.google.inject.Inject;

import de.thatsich.bachelor.classification.api.entities.IBinaryClassification;
import de.thatsich.bachelor.classification.api.models.IBinaryClassifications;
import de.thatsich.bachelor.classification.intern.command.ClassificationInitCommander;
import de.thatsich.bachelor.classification.intern.command.commands.SetLastBinaryClassificationIndexCommand;
import de.thatsich.bachelor.classification.intern.command.provider.IClassificationCommandProvider;
import de.thatsich.core.javafx.AFXMLPresenter;


/**
 * Code representation of ClassificationListView.
 * Displays the ClassificationList inside of a TableView
 * 
 * @author thatsIch
 */
public class ClassificationListPresenter extends AFXMLPresenter
{

	// Nodes
	@FXML private TableView<IBinaryClassification>				nodeTableViewBinaryClassificationList;

	@FXML private TableColumn<IBinaryClassification, String>	nodeTableColumnClassifierName;
	@FXML private TableColumn<IBinaryClassification, String>	nodeTableColumnExtractorName;
	@FXML private TableColumn<IBinaryClassification, Integer>	nodeTableColumnFrameSize;
	@FXML private TableColumn<IBinaryClassification, String>	nodeTableErrorName;
	@FXML private TableColumn<IBinaryClassification, String>	nodeTableColumnID;

	// Injects
	@Inject private IClassificationCommandProvider				commander;

	@Inject private IBinaryClassifications						binaryClassifications;

	@Inject ClassificationInitCommander							initCommander;

	// ==================================================
	// Initialization Implementation
	// ==================================================
	@Override
	protected void initComponents()
	{

	}

	@Override
	protected void bindComponents()
	{
		this.bindTableView();
	}

	/**
	 * TableView Bindings
	 */
	private void bindTableView()
	{
		this.bindTableViewContent();
		this.bindTableViewSelectionModel();
		this.bindTableViewCellValue();
	}

	/**
	 * Bind Content to TableView
	 */
	private void bindTableViewContent()
	{
		this.nodeTableViewBinaryClassificationList.itemsProperty().bind( this.binaryClassifications.getBinaryClassificationListProperty() );
		this.log.info( "Bound Content to Model." );
	}

	/**
	 * Bind Selection to Model and vice versa
	 */
	private void bindTableViewSelectionModel()
	{
		this.nodeTableViewBinaryClassificationList.getSelectionModel().selectedItemProperty().addListener( new ChangeListener<IBinaryClassification>()
		{
			@Override
			public void changed( ObservableValue<? extends IBinaryClassification> paramObservableValue, IBinaryClassification oldvalue, IBinaryClassification newValue )
			{
				binaryClassifications.setSelectedBinaryClassification( newValue );
				log.info( "Set Selected BinaryClassification in Model." );

				final int index = nodeTableViewBinaryClassificationList.getSelectionModel().getSelectedIndex();
				final SetLastBinaryClassificationIndexCommand command = commander.createSetLastBinaryClassificationIndexCommand( index );
				command.start();
			}
		} );
		this.log.info( "Bound Selection to Model." );

		this.binaryClassifications.getSelectedBinaryClassificationProperty().addListener( new ChangeListener<IBinaryClassification>()
		{
			@Override
			public void changed( ObservableValue<? extends IBinaryClassification> observable, IBinaryClassification oldValue, IBinaryClassification newValue )
			{
				nodeTableViewBinaryClassificationList.getSelectionModel().select( newValue );
			}
		} );
		this.log.info( "Bound Model to Selection." );
	}

	/**
	 * Bind Properties to TableColums
	 */
	private void bindTableViewCellValue()
	{
		this.nodeTableColumnClassifierName.setCellValueFactory( new PropertyValueFactory<IBinaryClassification, String>( "getClassificationName" ) );
		this.nodeTableColumnExtractorName.setCellValueFactory( new PropertyValueFactory<IBinaryClassification, String>( "getExtractorName" ) );
		this.nodeTableColumnFrameSize.setCellValueFactory( new PropertyValueFactory<IBinaryClassification, Integer>( "getFrameSize" ) );
		this.nodeTableErrorName.setCellValueFactory( new PropertyValueFactory<IBinaryClassification, String>( "getErrorName" ) );
		this.nodeTableColumnID.setCellValueFactory( new PropertyValueFactory<IBinaryClassification, String>( "getId" ) );
	}
}
