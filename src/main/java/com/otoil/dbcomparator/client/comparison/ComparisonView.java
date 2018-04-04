package com.otoil.dbcomparator.client.comparison;

import com.google.gwt.user.client.ui.IsWidget;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;

/**
 * Вьюшка с результатами сравнения баз.
 * @author kakeru
 *
 */
public interface ComparisonView extends IsWidget
{
    void setSourceDBRoot(DatabaseNode root);
    void setDestinationDBRoot(DatabaseNode root);
}
