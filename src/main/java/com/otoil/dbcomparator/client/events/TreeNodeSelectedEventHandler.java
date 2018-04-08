package com.otoil.dbcomparator.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface TreeNodeSelectedEventHandler extends EventHandler
{
    void onNodeSelectionChanged(TreeNodeSelectedEvent event);
}
