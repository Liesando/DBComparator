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
    private CustomCellTreeModel sourceModel = new CustomCellTreeModel();
    private CustomCellTreeModel destModel = new CustomCellTreeModel();
    private CellTree sourceTree = null;
    private CellTree destTree = null;

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
        if (sourceTree != null)
        {
            splitPanel.remove(sourceTree);
        }

        sourceModel.setRoot(root);
        sourceTree = new CellTree(sourceModel, null);
        openRecursevily(sourceTree);

        splitPanel.addWest(sourceTree, TREE_SIZE);
    }

    @Override
    public void setDestinationDBRoot(DatabaseNode root)
    {
        // удаляем предыдущие деревья, если таковые были,
        // чтобы не попасть в ситуацию с кучей копий
        // одного и того же CellTree
        if (destTree != null)
        {
            splitPanel.remove(destTree);
        }

        destModel.setRoot(root);
        destTree = new CellTree(destModel, null);
        openRecursevily(destTree);
        
        splitPanel.addEast(destTree, TREE_SIZE);
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
