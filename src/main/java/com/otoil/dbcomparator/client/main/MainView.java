package com.otoil.dbcomparator.client.main;


import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * Главная вьюха приложухи
 * 
 * @author Sergey Medelyan
 */
public interface MainView extends IsWidget
{
    AcceptsOneWidget snapshotsContainer();

    AcceptsOneWidget comparisonContainer();

    AcceptsOneWidget detailsContainer();
}
