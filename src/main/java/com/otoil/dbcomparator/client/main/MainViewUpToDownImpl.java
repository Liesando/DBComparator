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
    private static final String DETAILS_CONTAINER_HEIGHT = "200px";
    private VerticalPanel verticalPanel = new VerticalPanel();
    private SimplePanel snapshotsContainer = new SimplePanel();
    private SimplePanel comparisonContainer = new SimplePanel();
    private SimplePanel detailsContainer = new SimplePanel();

    public MainViewUpToDownImpl()
    {
        verticalPanel.setWidth("100%");
        snapshotsContainer.setWidth("100%");
        comparisonContainer.setWidth("100%");
        detailsContainer.setWidth("100%");
        verticalPanel.add(snapshotsContainer);
        verticalPanel.add(comparisonContainer);
        verticalPanel.add(detailsContainer);
        snapshotsContainer.setHeight(SNAPSHOTS_CONTAINER_HEIGHT);
        comparisonContainer.setHeight(COMPARISON_CONTAINER_HEIGHT);
        detailsContainer.setHeight(DETAILS_CONTAINER_HEIGHT);
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

    @Override
    public AcceptsOneWidget detailsContainer()
    {
        return detailsContainer;
    }
}
