package com.otoil.dbcomparator.client.main;


import com.google.gwt.event.shared.EventBus;
import com.otoil.dbcomparator.client.snapshots.SnapshotsView;
import com.otoil.dbcomparator.client.snapshots.SnapshotsUniqueNameGenerator;
import com.otoil.dbcomparator.client.comparison.ComparisonModel;
import com.otoil.dbcomparator.client.comparison.ComparisonView;
import com.otoil.dbcomparator.client.details.DetailsView;
import com.otoil.dbcomparator.client.snapshots.SnapshotsModel;


/**
 * Фабрика, которая предоставит нужные вьюхи, модели и прочее в зависимости от
 * конкретной реализации (например, для десктопа + .zip-архивов со слепками)
 * 
 * @author Sergey Medelyan
 */
public interface ClientFactory
{
    EventBus getEventBus();

    MainView getMainView();

    SnapshotsView getSnapshotsView();

    SnapshotsModel getSnapshotsModel();

    SnapshotsUniqueNameGenerator getSnapshotsUniqueNameGenerator();

    ComparisonView getComparisonView();

    ComparisonModel getComparisonModel();

    DetailsView getDetailsView();
}