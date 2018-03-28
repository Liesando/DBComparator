package com.otoil.dbcomparator.client.interfaces;

import com.google.gwt.user.client.ui.IsWidget;
import com.otoil.dbcomparator.shared.DatabaseNode;

/**
 * Вьюшка с результатами сравнения баз.
 * @author kakeru
 *
 */
public interface ResultView extends IsWidget
{
    void setSourceDBRoot(DatabaseNode root);
    void setDestinationDBRoot(DatabaseNode root);
}
