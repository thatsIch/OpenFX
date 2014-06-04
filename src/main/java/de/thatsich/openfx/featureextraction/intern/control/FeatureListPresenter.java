package de.thatsich.openfx.featureextraction.intern.control;

import com.google.inject.Inject;
import de.thatsich.core.javafx.AFXMLPresenter;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeature;
import de.thatsich.openfx.featureextraction.api.control.entity.IFeatureVector;
import de.thatsich.openfx.featureextraction.api.model.IFeatures;
import de.thatsich.openfx.featureextraction.intern.control.command.FeatureInitCommander;
import de.thatsich.openfx.featureextraction.intern.control.command.IFeatureCommandProvider;
import de.thatsich.openfx.featureextraction.intern.control.command.commands.SetLastFeatureVectorIndexCommand;
import de.thatsich.openfx.featureextraction.intern.view.tree.FeatureVectorSetTreeItemAdapter;
import de.thatsich.openfx.featureextraction.intern.view.tree.FeatureVectorTreeItemAdapter;
import de.thatsich.openfx.featureextraction.intern.view.tree.IFeatureSpaceTreeItemAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class FeatureListPresenter extends AFXMLPresenter
{
	@Inject private FeatureInitCommander initCommander;
	@Inject private IFeatures features;
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
			public IFeatureVector getVector() { return null; }

			@Override
			public IFeature getFeature() { return null; }
		});
		this.nodeTreeViewRoot.setExpanded(true);
		this.nodeTreeViewFeatureVectorSetList.setRoot(this.nodeTreeViewRoot);
	}

	private void bindTreeViewModel()
	{
		this.features.list().addListener((ListChangeListener<IFeature>) paramChange -> {
			while (paramChange.next())
			{
				if (paramChange.wasAdded())
				{
					this.log.info("Items were added to TreeView.");

					final List<? extends IFeature> featureVectorSetList = paramChange.getAddedSubList();
					this.log.info("List Size: " + featureVectorSetList.size());

					for (IFeature feature : featureVectorSetList)
					{
						this.log.info(feature.toString());
						final List<IFeatureVector> featureVectorList = feature.vectors();
						final TreeItem<IFeatureSpaceTreeItemAdapter> setTreeItem = new TreeItem<>(new FeatureVectorSetTreeItemAdapter(feature));
						setTreeItem.setExpanded(true);

						for (IFeatureVector vector : featureVectorList)
						{
							setTreeItem.getChildren().add(new TreeItem<>(new FeatureVectorTreeItemAdapter(vector)));
						}

						this.nodeTreeViewRoot.getChildren().add(setTreeItem);
						this.nodeTreeViewFeatureVectorSetList.getSelectionModel().select(setTreeItem);
					}
				}
				else if (paramChange.wasRemoved())
				{
					this.log.info("Items were removed from TreeView.");

					final List<? extends IFeature> removedSubList = paramChange.getRemoved();
					final List<TreeItem<IFeatureSpaceTreeItemAdapter>> removing = FXCollections.observableArrayList();

					for (TreeItem<IFeatureSpaceTreeItemAdapter> child : this.nodeTreeViewRoot.getChildren())
					{
						for (IFeature set : removedSubList)
						{
							final IFeature childSet = child.getValue().getFeature();

							if (set.equals(childSet))
							{
								removing.add(child);
							}
						}
					}

					for (TreeItem<IFeatureSpaceTreeItemAdapter> removeChild : removing)
					{
						this.nodeTreeViewRoot.getChildren().remove(removeChild);
					}

					if (!this.nodeTreeViewRoot.getChildren().isEmpty())
					{
						this.nodeTreeViewFeatureVectorSetList.getSelectionModel().select(0);
						return;
					}
				}
			}
		});
		this.log.info("Bound TreeView to FeatureSpace.");
	}

	private void bindTreeViewSelection()
	{
		this.features.index().addListener((paramObservableValue, paramT1, paramT2) -> this.nodeTreeViewFeatureVectorSetList.getSelectionModel().select(paramT2.intValue()));

		this.nodeTreeViewFeatureVectorSetList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null)
			{
				return;
			}

			final IFeatureSpaceTreeItemAdapter item = newValue.getValue();

			if (item.isSet())
			{
				this.features.selectedFeature().set(item.getFeature());
				this.features.selectedVector().set(null);
			}
			else if (item.isVector())
			{
				final IFeatureSpaceTreeItemAdapter set = newValue.getParent().getValue();
				if (!set.isSet())
				{
					throw new IllegalStateException("Superset of a FeatureVector should be a FeatureVectorSet.");
				}

				this.features.selectedFeature().set(set.getFeature());
				this.features.selectedVector().set(item.getVector());
			}

			// is root or something else
			else
			{
				this.features.selectedFeature().set(null);
				this.features.selectedVector().set(null);
			}

			final int selectedIndex = this.nodeTreeViewFeatureVectorSetList.getSelectionModel().getSelectedIndex();
			final SetLastFeatureVectorIndexCommand command = this.commander.createSetLastFeatureVectorIndexCommand(selectedIndex);
			command.start();
		});
	}
}
