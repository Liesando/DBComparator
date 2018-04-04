package com.otoil.dbcomparator.client.main;


import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.otoil.dbcomparator.client.snapshots.SnapshotsView;
import com.otoil.dbcomparator.client.snapshots.SnapshotsViewZipImpl;
import com.otoil.dbcomparator.client.snapshots.SnapshotsUniqueNameGenerator;
import com.otoil.dbcomparator.client.snapshots.SnapshotsUniqueNameGeneratorMockImpl;
import com.otoil.dbcomparator.client.comparison.ComparisonModel;
import com.otoil.dbcomparator.client.comparison.ComparisonModelRestImpl;
import com.otoil.dbcomparator.client.comparison.ComparisonView;
import com.otoil.dbcomparator.client.comparison.ComparisonViewDesktopImpl;
import com.otoil.dbcomparator.client.snapshots.SnapshotsModel;
import com.otoil.dbcomparator.client.snapshots.SnapshotsModelZipRestImpl;


/**
 * Дефолтная фабрика.
 * 
 * @author kakeru
 */
public class ClientFactoryDesktopSnapshotsImpl implements ClientFactory
{
    private final EventBus eventBus = new SimpleEventBus();
    private final MainView mainView = new MainViewUpToDownImpl();
    private final SnapshotsView snapshotsView = new SnapshotsViewZipImpl();
    private final SnapshotsModel snapshotsModel = new SnapshotsModelZipRestImpl();
    private final SnapshotsUniqueNameGenerator snapshotsUniqueNameGenerator = new SnapshotsUniqueNameGeneratorMockImpl();
    private final ComparisonView comparisonView = new ComparisonViewDesktopImpl();
    private final ComparisonModel comparisonModel = new ComparisonModelRestImpl();

    public ClientFactoryDesktopSnapshotsImpl()
    {
    }

    @Override
    public EventBus getEventBus()
    {
        return eventBus;
    }

    @Override
    public MainView getMainView()
    {
        return mainView;
    }

    @Override
    public SnapshotsView getSnapshotsView()
    {
        return snapshotsView;
    }

    @Override
    public SnapshotsModel getSnapshotsModel()
    {
        return snapshotsModel;
    }

    @Override
    public SnapshotsUniqueNameGenerator getSnapshotsUniqueNameGenerator()
    {
        return snapshotsUniqueNameGenerator;
    }

    @Override
    public ComparisonView getComparisonView()
    {
        return comparisonView;
    }

    @Override
    public ComparisonModel getComparisonModel()
    {
        return comparisonModel;
    }

}
