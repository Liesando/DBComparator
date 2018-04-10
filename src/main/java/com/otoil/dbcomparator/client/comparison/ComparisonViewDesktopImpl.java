package com.otoil.dbcomparator.client.comparison;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;


/**
 * Вьюха результатов сравнения для ПК. TODO: зафиксить странное поведение с
 * двумя сплиттерами вместо одного О.о
 * 
 * @author kakeru
 */
public class ComparisonViewDesktopImpl implements ComparisonView
{
    private static final double TREE_SIZE = 320.0;
    private VerticalPanel verticalPanel = new VerticalPanel();
    private SplitLayoutPanel splitPanel = new SplitLayoutPanel();
    private CustomCellTreeModel sourceCellTreeModel = new CustomCellTreeModel();
    private CustomCellTreeModel destCellTreeModel = new CustomCellTreeModel();
    private ScrollPanel sourceScrollPanel = new ScrollPanel();
    private ScrollPanel destScrollPanel = new ScrollPanel();
    private CellTree sourceCellTree = null;
    private CellTree destCellTree = null;
    private DBComparatorMessages messages = GWT
        .create(DBComparatorMessages.class);

    public ComparisonViewDesktopImpl()
    {
        verticalPanel.setWidth("100%");
        verticalPanel.setHeight("500px");
        
        CheckBox hideNonChangedCB = new CheckBox(messages.hideNonChanged());
        hideNonChangedCB.addClickHandler(event -> {
            boolean checked = ((CheckBox) event.getSource()).getValue();
            sourceCellTreeModel.setHideNonChanged(checked);
            destCellTreeModel.setHideNonChanged(checked);

            if (sourceCellTree != null && destCellTree != null)
            {
                setSourceDBRoot(sourceCellTreeModel.getRoot());
                setDestinationDBRoot(destCellTreeModel.getRoot());
            }
        });
        
        splitPanel.setHeight("450px");
        splitPanel.setWidth("100%");
        splitPanel.addWest(sourceScrollPanel, TREE_SIZE);
        splitPanel.add(destScrollPanel);
        
        verticalPanel.add(hideNonChangedCB);
        verticalPanel.add(splitPanel);
    }

    @Override
    public void addSourceTreeSelectionChangedHandler(
        CellTreeSelectionChangedHandler handler)
    {
        sourceCellTreeModel.addSelectionChangeHandler(handler);

    }

    @Override
    public void addDestTreeSelectionChangedHandler(
        CellTreeSelectionChangedHandler handler)
    {
        destCellTreeModel.addSelectionChangeHandler(handler);
    }

    @Override
    public void setSourceDBRoot(DatabaseNode root)
    {
        sourceCellTree = constructCellTree(root, sourceCellTreeModel);
        sourceScrollPanel.setWidget(sourceCellTree);
    }

    @Override
    public void setDestinationDBRoot(DatabaseNode root)
    {
        destCellTree = constructCellTree(root, destCellTreeModel);
        destScrollPanel.setWidget(destCellTree);
    }

    private CellTree constructCellTree(DatabaseNode root,
        CustomCellTreeModel cellTreeModel)
    {
        cellTreeModel.setRoot(root);
        CellTree cellTree = new CellTree(cellTreeModel, null);
        cellTree.getRootTreeNode().setChildOpen(0, true);
        return cellTree;
    }

    @Override
    public Widget asWidget()
    {
        return verticalPanel;
    }
}
