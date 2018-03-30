package com.otoil.dbcomparator.client;


import java.util.ArrayList;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.otoil.dbcomparator.client.interfaces.ResultView;
import com.otoil.dbcomparator.shared.DatabaseNode;


/**
 * Вьюха результатов сравнения для ПК.
 * 
 * TODO: зафиксить странное поведение с двумя сплиттерами вместо одного О.о
 * 
 * @author kakeru
 */
public class ResultViewDesktopImpl implements ResultView
{
    private static final double TREE_SIZE = 256.0;
    private SplitLayoutPanel splitPanel = new SplitLayoutPanel();
    private CustomCellTreeModel sourceCellTreeModel = new CustomCellTreeModel();
    private CustomCellTreeModel destCellTreeModel = new CustomCellTreeModel();
    private CellTree sourceCellTree = null;
    private CellTree destCellTree = null;

    public ResultViewDesktopImpl()
    {
        splitPanel.setHeight("100%");
    }

    @Override
    public void setSourceDBRoot(DatabaseNode root)
    {
        // удаляем предыдущие деревья, если таковые были,
        // чтобы не попасть в ситуацию с кучей копий
        // одного и того же CellTree
        if (sourceCellTree != null)
        {
            splitPanel.remove(sourceCellTree);
        }

        sourceCellTreeModel.setRoot(root);
        sourceCellTree = new CellTree(sourceCellTreeModel, null);
        openRecursevily(sourceCellTree);

        splitPanel.addWest(sourceCellTree, TREE_SIZE);
    }

    @Override
    public void setDestinationDBRoot(DatabaseNode root)
    {
        // удаляем предыдущие деревья, если таковые были,
        // чтобы не попасть в ситуацию с кучей копий
        // одного и того же CellTree
        if (destCellTree != null)
        {
            splitPanel.remove(destCellTree);
        }

        destCellTreeModel.setRoot(root);
        destCellTree = new CellTree(destCellTreeModel, null);
        openRecursevily(destCellTree);
        
        splitPanel.addEast(destCellTree, TREE_SIZE);
    }

    /**
     * Полностью раскрывает указанное дерево
     * @param tree
     */
    private void openRecursevily(CellTree tree)
    {

        // хоть в названии и написано "рекурсивно", рекурсию мы не используем,
        // ибо зло. Ползём циклом.

        ArrayList<TreeNode> compositeNodes = new ArrayList<TreeNode>();
        compositeNodes.add(tree.getRootTreeNode());
        int i = 0;
        while (i < compositeNodes.size())
        {
            int childCount = compositeNodes.get(i).getChildCount();
            for (int j = 0; j < childCount; j++)
            {
                TreeNode compositeChild = compositeNodes.get(i).setChildOpen(j,
                    true);
                if (compositeChild != null)
                {
                    compositeNodes.add(compositeChild);
                }
            }

            i++;
        }
    }

    @Override
    public Widget asWidget()
    {
        return splitPanel;
    }
}
