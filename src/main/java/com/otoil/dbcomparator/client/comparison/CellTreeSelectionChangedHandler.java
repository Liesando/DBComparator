package com.otoil.dbcomparator.client.comparison;

import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.otoil.dbcomparator.shared.beans.AbstractNode;

@FunctionalInterface
public interface CellTreeSelectionChangedHandler
{
    void onSelectionChanged(SingleSelectionModel<AbstractNode> selectionModel, SelectionChangeEvent event);
}
