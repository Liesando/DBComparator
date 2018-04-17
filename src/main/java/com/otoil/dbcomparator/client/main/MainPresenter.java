package com.otoil.dbcomparator.client.main;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.otoil.dbcomparator.client.comparison.ComparisonPresenter;
import com.otoil.dbcomparator.client.details.DetailsPresenter;
import com.otoil.dbcomparator.client.snapshots.SnapshotsPresenter;


/**
 * Главный презентер приложухи
 * 
 * @author Sergey Medelyan
 */
public class MainPresenter extends AbstractActivity
{
    private MainView mainView;
    private SnapshotsPresenter snapshotsPresenter;
    private ComparisonPresenter comparisonPresenter;
    private DetailsPresenter detailsPresenter;

    public MainPresenter(ClientFactory factory)
    {
        mainView = factory.getMainView();
        snapshotsPresenter = new SnapshotsPresenter(factory);
        comparisonPresenter = new ComparisonPresenter(factory);
        detailsPresenter = new DetailsPresenter(factory);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus)
    {
        panel.setWidget(mainView);
        snapshotsPresenter.start(mainView.snapshotsContainer(), eventBus);
        comparisonPresenter.start(mainView.comparisonContainer(), eventBus);
        detailsPresenter.start(mainView.detailsContainer(), eventBus);
    }

}
