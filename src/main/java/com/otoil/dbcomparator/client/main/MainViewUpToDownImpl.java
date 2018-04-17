package com.otoil.dbcomparator.client.main;


import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Дефолтная главная вьюха приложухи, располагает все контейнеры сверху вниз
 * 
 * @author Sergey Medelyan
 */
public class MainViewUpToDownImpl implements MainView
{
    private static final int DETAILS_CONTAINER_SIZE = 200;
    private static final String SNAPSHOTS_CONTAINER_HEIGHT = "32px";
    private VerticalPanel verticalPanel = new VerticalPanel();
    private SimplePanel snapshotsContainer = new SimplePanel();
    private SimplePanel comparisonContainer = new SimplePanel();
    private SimplePanel detailsContainer = new SimplePanel();

    public MainViewUpToDownImpl()
    {
        verticalPanel.setSize("100%", "100%");
        snapshotsContainer.setSize("100%", SNAPSHOTS_CONTAINER_HEIGHT);
        comparisonContainer.setSize("100%", "100%");
        detailsContainer.setSize("100%", "100%");

        SplitLayoutPanel splitLayoutPanel = new SplitLayoutPanel(8);
        splitLayoutPanel.addSouth(detailsContainer, DETAILS_CONTAINER_SIZE);
        splitLayoutPanel.add(comparisonContainer);

        verticalPanel.add(snapshotsContainer);
        verticalPanel.add(splitLayoutPanel);

        verticalPanel.setCellHeight(splitLayoutPanel, "100%");
        splitLayoutPanel.setSize("100%", "100%");
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
