package com.otoil.dbcomparator.client.events;


import com.google.gwt.event.shared.GwtEvent;
import com.otoil.dbcomparator.shared.beans.AbstractNode;


public class TreeNodeSelectedEvent
        extends GwtEvent<TreeNodeSelectedEventHandler>
{
    public static final Type<TreeNodeSelectedEventHandler> TYPE = new Type<TreeNodeSelectedEventHandler>();

    private AbstractNode selectedNode;
    
    public AbstractNode getSelectedNode()
    {
        return selectedNode;
    }

    public void setSelectedNode(AbstractNode selectedNode)
    {
        this.selectedNode = selectedNode;
    }

    @Override
    public Type<TreeNodeSelectedEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    @Override
    protected void dispatch(TreeNodeSelectedEventHandler handler)
    {
        handler.onNodeSelectionChanged(this);
    }

}
