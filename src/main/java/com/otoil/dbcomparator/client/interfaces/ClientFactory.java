package com.otoil.dbcomparator.client.interfaces;

import com.google.gwt.event.shared.EventBus;

/**
 * Фабрика, которая предоставит нужные вьюхи,
 * модели и прочее в зависимости от конкретной реализации
 * (например, для десктопа + .zip-архивов со слепками)
 * @author kakeru
 */
public interface ClientFactory
{
    EventBus getEventBus();
    DBSourcesView getDBSourcesView();
    SnapshotsModel getSnapshotsModel();
    UniqueNameGenerator getNameGenerator();
}