package com.otoil.dbcomparator.client.comparison;


import java.util.ArrayList;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
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
    private SplitLayoutPanel splitPanel = new SplitLayoutPanel();
    private CustomCellTreeModel sourceCellTreeModel = new CustomCellTreeModel();
    private CustomCellTreeModel destCellTreeModel = new CustomCellTreeModel();
    private ScrollPanel sourceScrollPanel = new ScrollPanel();
    private ScrollPanel destScrollPanel = new ScrollPanel();
    private CellTree sourceCellTree = null;
    private CellTree destCellTree = null;

    public ComparisonViewDesktopImpl()
    {
        splitPanel.setHeight("500px");
        splitPanel.setWidth("100%");
//        splitPanel.addWest(sourceScrollPanel, splitPanel.getOffsetWidth() / 2);
        splitPanel.addWest(sourceScrollPanel, TREE_SIZE);
        splitPanel.add(destScrollPanel);
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
        return splitPanel;
    }
}
