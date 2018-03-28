package com.otoil.dbcomparator.client.interfaces;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Вьюшка для указания источника слепков баз.
 * @author kakeru
 *
 */
public interface DBSourcesView extends IsWidget
{
    HandlerRegistration addSubmitCompleteHandler(SubmitCompleteHandler handler);
    HasName source();
    HasName destination();
}
