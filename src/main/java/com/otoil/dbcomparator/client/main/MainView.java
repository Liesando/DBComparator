package com.otoil.dbcomparator.client.main;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.otoil.dbcomparator.client.comparison.ComparisonView;
import com.otoil.dbcomparator.client.snapshots.SnapshotsView;

public interface MainView extends IsWidget
{
    AcceptsOneWidget snapshotsContainer();
    AcceptsOneWidget comparisonContainer();
}
