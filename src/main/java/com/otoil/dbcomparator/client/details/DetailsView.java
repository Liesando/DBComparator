package com.otoil.dbcomparator.client.details;

import com.google.gwt.user.client.ui.IsWidget;
import com.otoil.dbcomparator.shared.beans.AbstractNode;

public interface DetailsView extends IsWidget
{
    <T extends AbstractNode> void displayNodeDetails(T node);
}
