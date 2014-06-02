package de.thatsich.bachelor.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVector;
import de.thatsich.bachelor.featureextraction.api.control.FeatureVectorSet;
import de.thatsich.bachelor.featureextraction.api.model.IFeatureVectorSets;
import de.thatsich.bachelor.featureextraction.intern.control.command.FeatureInitCommander;
import de.thatsich.bachelor.featureextraction.intern.control.command.IFeatureCommandProvider;
import de.thatsich.bachelor.featureextraction.intern.control.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.bachelor.featureextraction.intern.views.tree.FeatureVectorSetTreeItemAdapter;
import de.thatsich.bachelor.featureextraction.intern.views.tree.FeatureVectorTreeItemAdapter;
import de.thatsich.bachelor.featureextraction.intern.views.tree.IFeatureSpaceTreeItemAdapter;
import de.thatsich.core.javafx.AFXMLPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class FeatureListPresenter extends AFXMLPresenter
{
	@Inject private FeatureInitCommander initCommander;
	@Inject private IFeatureVectorSets featureVectors;
	@Inject private IFeatureCommandProvider commander;

	// Nodes
	@FXML private TreeView<IFeatureSpaceTreeItemAdapter> nodeTreeViewFeatureVectorSetList;
	private TreeItem<IFeatureSpaceTreeItemAdapter> nodeTreeViewRoot;

	@Override
	protected void bindComponents()
	{
		this.bindTreeViewModel();
		this.bindTreeViewSelection();
	}

	@Override
	protected void initComponents()
	{
		this.initTreeView();
	}

	private void initTreeView()
	{
		this.nodeTreeViewRoot = new TreeItem<>(new IFeatureSpaceTreeItemAdapter()
		{
			@Override
			public String toString() { return "FeatureVector Sets"; }

			@Override
			public boolean isVector() { return false; }

			@Override
			public boolean isSet() { return false; }

			@Override
			public FeatureVector getVector() { return null; }

			@Override
			public FeatureVectorSet getSet() { return null; }
		});
		this.nodeTreeViewRoot.setExpanded(true);
		this.nodeTreeViewFeatureVectorSetList.setRoot(nodeTreeViewRoot);
	}

	private void bindTreeViewModel()
	{
		this.featureVectors.list().addListener((ListChangeListener<FeatureVectorSet>) paramChange -> {
			while (paramChange.next())
			{
				if (paramChange.wasAdded())
				{
					log.info("Items were added to TreeView.");

					final List<? extends FeatureVectorSet> featureVectorSetList = paramChange.getAddedSubList();
					log.info("List Size: " + featureVectorSetList.size());

					for (FeatureVectorSet set : featureVectorSetList)
					{
						log.info(set.toString());
						final List<FeatureVector> featureVectorList = set.featureVectors();
						final TreeItem<IFeatureSpaceTreeItemAdapter> setTreeItem = new TreeItem<>(new FeatureVectorSetTreeItemAdapter(set));
						setTreeItem.setExpanded(true);

						for (FeatureVector vector : featureVectorList)
						{
							setTreeItem.getChildren().add(new TreeItem<>(new FeatureVectorTreeItemAdapter(vector)));
						}

						nodeTreeViewRoot.getChildren().add(setTreeItem);
						nodeTreeViewFeatureVectorSetList.getSelectionModel().select(setTreeItem);
					}
				}
				else if (paramChange.wasRemoved())
				{
					log.info("Items were removed from TreeView.");

					final List<? extends FeatureVectorSet> removedSubList = paramChange.getRemoved();
					final List<TreeItem<IFeatureSpaceTreeItemAdapter>> removing = FXCollections.observableArrayList();

					for (TreeItem<IFeatureSpaceTreeItemAdapter> child : nodeTreeViewRoot.getChildren())
					{
						for (FeatureVectorSet set : removedSubList)
						{
							final FeatureVectorSet childSet = child.getValue().getSet();

							if (set.equals(childSet))
							{
								removing.add(child);
							}
						}
					}

					for (TreeItem<IFeatureSpaceTreeItemAdapter> removeChild : removing)
					{
						nodeTreeViewRoot.getChildren().remove(removeChild);
					}

					if (!nodeTreeViewRoot.getChildren().isEmpty())
					{
						nodeTreeViewFeatureVectorSetList.getSelectionModel().select(0);
						return;
					}
				}
			}
		});
		this.log.info("Bound TreeView to FeatureSpace.");
	}

	private void bindTreeViewSelection()
	{
		this.featureVectors.index().addListener((paramObservableValue, paramT1, paramT2) -> nodeTreeViewFeatureVectorSetList.getSelectionModel().select(paramT2.intValue()));

		this.nodeTreeViewFeatureVectorSetList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null)
			{
				return;
			}

			final IFeatureSpaceTreeItemAdapter item = newValue.getValue();

			if (item.isSet())
			{
				featureVectors.selectedSet().set(item.getSet());
				featureVectors.selected().set(null);
			}
			else if (item.isVector())
			{
				final IFeatureSpaceTreeItemAdapter set = newValue.getParent().getValue();
				if (!set.isSet())
				{
					throw new IllegalStateException("Superset of a FeatureVector should be a FeatureVectorSet.");
				}

				featureVectors.selectedSet().set(set.getSet());
				featureVectors.selected().set(item.getVector());
			}

			// is root or something else
			else
			{
				featureVectors.selectedSet().set(null);
				featureVectors.selected().set(null);
			}

			final int selectedIndex = nodeTreeViewFeatureVectorSetList.getSelectionModel().getSelectedIndex();
			final SetLastFeatureVectorIndexCommand command = commander.createSetLastFeatureVectorIndexCommand(selectedIndex);
			command.start();
		});
	}
}
