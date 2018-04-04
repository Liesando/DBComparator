package com.otoil.dbcomparator.client.main;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.otoil.dbcomparator.client.comparison.ComparisonView;
import com.otoil.dbcomparator.client.snapshots.SnapshotsView;

public class MainViewUpToDownImpl implements MainView
{
    private static final String SNAPSHOTS_CONTAINER_HEIGHT = "32px";
    private static final String COMPARISON_CONTAINER_HEIGHT = "500px";
    private VerticalPanel verticalPanel = new VerticalPanel();
    private SimplePanel snapshotsContainer = new SimplePanel();
    private SimplePanel comparisonContainer = new SimplePanel();

    public MainViewUpToDownImpl()
    {
        verticalPanel.add(snapshotsContainer);
        verticalPanel.add(comparisonContainer);
        snapshotsContainer.setHeight(SNAPSHOTS_CONTAINER_HEIGHT);
        comparisonContainer.setHeight(COMPARISON_CONTAINER_HEIGHT);
    }
    
    @Override
    public AcceptsOneWidget snapshotsContainer()
    {
        return snapshotsContainer;
    }

    @Override
    public AcceptsOneWidget comparisonContainer()
    {
        return comparisonContainer;
    }

    @Override
    public Widget asWidget()
    {
        return verticalPanel;
    }
}
