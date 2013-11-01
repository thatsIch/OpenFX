package de.thatsich.bachelor.featureextraction.restricted.controller;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import com.google.inject.Inject;

import de.thatsich.bachelor.featureextraction.api.core.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.entities.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.restricted.command.FeatureCommandProvider;
import de.thatsich.bachelor.featureextraction.restricted.command.FeatureInitCommander;
import de.thatsich.bachelor.featureextraction.restricted.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.restricted.views.tree.FeatureVectorSetTreeItemAdapter;
import de.thatsich.bachelor.featureextraction.restricted.views.tree.FeatureVectorTreeItemAdapter;
import de.thatsich.bachelor.featureextraction.restricted.views.tree.IFeatureSpaceTreeItemAdapter;
import de.thatsich.core.javafx.AFXMLPresenter;

public class FeatureListPresenter extends AFXMLPresenter {

	// Nodes
	@FXML private TreeView<IFeatureSpaceTreeItemAdapter> nodeTreeViewFeatureVectorSetList;
	private TreeItem<IFeatureSpaceTreeItemAdapter> nodeTreeViewRoot;
	
	// Model
	@Inject private IFeatureVectorSets featureVectors;
	
	// Command
	@Inject private FeatureCommandProvider commander;
	@Inject FeatureInitCommander initCommander;
	
	@Override
	protected void initComponents() {
		this.initTreeView();
	}

	@Override
	protected void bindComponents() {
		this.bindTreeViewModel();
		this.bindTreeViewSelection();
	}
	
	private void initTreeView() {
		this.nodeTreeViewRoot = new TreeItem<IFeatureSpaceTreeItemAdapter>(new IFeatureSpaceTreeItemAdapter() {
			@Override public String toString() { return "FeatureVector Sets"; }
			@Override public boolean isVector() { return false; }
			@Override public boolean isSet() { return false; }
			@Override public FeatureVector getVector() { return null; }
			@Override public FeatureVectorSet getSet() { return null; }
		});
		this.nodeTreeViewRoot.setExpanded(true);
		this.nodeTreeViewFeatureVectorSetList.setRoot(nodeTreeViewRoot);
	}
	
	private void bindTreeViewModel() {
		this.featureVectors.getFeatureVectorSetListProperty().addListener(new ListChangeListener<FeatureVectorSet>() {
			@Override public void onChanged(Change<? extends FeatureVectorSet> paramChange) {
				while (paramChange.next()) {
					if (paramChange.wasAdded()) {
						log.info("Items were added to TreeView.");
						
						final List<? extends FeatureVectorSet> featureVectorSetList = paramChange.getAddedSubList();
						log.info("List Size: " + featureVectorSetList.size());
						
						for (FeatureVectorSet set : featureVectorSetList) {
							log.info(set.toString());
							final List<FeatureVector> featureVectorList = set.getFeatureVectorList();
							final TreeItem<IFeatureSpaceTreeItemAdapter> setTreeItem = new TreeItem<IFeatureSpaceTreeItemAdapter>(new FeatureVectorSetTreeItemAdapter(set));
							setTreeItem.setExpanded(true);
							
							for (FeatureVector vector : featureVectorList) {
								setTreeItem.getChildren().add(new TreeItem<IFeatureSpaceTreeItemAdapter>(new FeatureVectorTreeItemAdapter(vector)));
							}
				        	
							nodeTreeViewRoot.getChildren().add(setTreeItem);
							nodeTreeViewFeatureVectorSetList.getSelectionModel().select(setTreeItem);
						}
					}
					else if (paramChange.wasRemoved()) {
						log.info("Items were removed from TreeView.");
						
						final List<? extends FeatureVectorSet> removedSubList = paramChange.getRemoved();
						final List<TreeItem<IFeatureSpaceTreeItemAdapter>> removing = FXCollections.observableArrayList();
						
						for (TreeItem<IFeatureSpaceTreeItemAdapter> child : nodeTreeViewRoot.getChildren()) {
							for (FeatureVectorSet set : removedSubList) {
								final FeatureVectorSet childSet = child.getValue().getSet(); 
								
								if (set.equals(childSet)) {
									removing.add(child);
								}
							}
						}
						
						for (TreeItem<IFeatureSpaceTreeItemAdapter> removeChild : removing) {
							nodeTreeViewRoot.getChildren().remove(removeChild);
						}
						
						for (TreeItem<IFeatureSpaceTreeItemAdapter> child : nodeTreeViewRoot.getChildren()) {
							nodeTreeViewFeatureVectorSetList.getSelectionModel().select(child);
							return;
						}
					}
				}
 			}

		});
		this.log.info("Bound TreeView to FeatureSpace.");
	}
	
	private void bindTreeViewSelection() {
		this.featureVectors.getSelectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramT1, Number paramT2) {
				nodeTreeViewFeatureVectorSetList.getSelectionModel().select(paramT2.intValue());
			}
		});
		
		this.nodeTreeViewFeatureVectorSetList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<IFeatureSpaceTreeItemAdapter>>() {
			@Override public void changed(ObservableValue<? extends TreeItem<IFeatureSpaceTreeItemAdapter>> observable, TreeItem<IFeatureSpaceTreeItemAdapter> oldValue, TreeItem<IFeatureSpaceTreeItemAdapter> newValue) {
				if (newValue == null) return;
				
				final IFeatureSpaceTreeItemAdapter item = newValue.getValue();
				
				if (item.isSet()) {
					featureVectors.getSelectedFeatureVectorSetProperty().set(item.getSet());
					featureVectors.getSelectedFeatureVectorProperty().set(null);
				} 
				else if (item.isVector()) {
					final IFeatureSpaceTreeItemAdapter set = newValue.getParent().getValue();
					if (!set.isSet()) throw new IllegalStateException("Superset of a FeatureVector should be a FeatureVectorSet.");
					
					featureVectors.getSelectedFeatureVectorSetProperty().set(set.getSet());
					featureVectors.getSelectedFeatureVectorProperty().set(item.getVector());
				}
				
				// is root or something else
				else {
					featureVectors.getSelectedFeatureVectorSetProperty().set(null);
					featureVectors.getSelectedFeatureVectorProperty().set(null);
				}
				
				final int selectedIndex = nodeTreeViewFeatureVectorSetList.getSelectionModel().getSelectedIndex();
				final SetLastFeatureVectorIndexCommand command = commander.createSetLastFeatureVectorIndexCommand(selectedIndex);
				command.start();
			}
		});
	}
}
