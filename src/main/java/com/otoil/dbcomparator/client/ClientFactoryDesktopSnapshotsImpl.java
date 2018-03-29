package com.otoil.dbcomparator.client;


import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.otoil.dbcomparator.client.interfaces.ClientFactory;
import com.otoil.dbcomparator.client.interfaces.DBSourcesView;
import com.otoil.dbcomparator.client.interfaces.SnapshotsModel;
import com.otoil.dbcomparator.client.interfaces.UniqueNameGenerator;


/**
 * Дефолтная фабрика, предоставляющая 
 * {@link DBSourcesView},
 * {@link EventBus eventBus},
 * {@link UniqueNameGenerator} и
 * {@link SnapshotsModel}
 * @author kakeru
 *
 */
public class ClientFactoryDesktopSnapshotsImpl implements ClientFactory
{
    private final EventBus eventBus = new SimpleEventBus();
    private final DBSourcesView view = new DBSourcesViewZipSnapshotsImpl();
    private final UniqueNameGenerator generator = new UniqueNameGeneratorMockImpl();
    private final SnapshotsModel model = new SnapshotsModelZipRestImpl();
    
    public ClientFactoryDesktopSnapshotsImpl() {}
    
    @Override
    public EventBus getEventBus()
    {
        return eventBus;
    }

    @Override
    public DBSourcesView getDBSourcesView()
    {
        return view;
    }

    @Override
    public SnapshotsModel getSnapshotsModel()
    {
        return model;
    }

    @Override
    public UniqueNameGenerator getNameGenerator()
    {
        return generator;
    }
    
}
