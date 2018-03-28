package com.otoil.dbcomparator.client;


import com.google.gwt.event.shared.GwtEvent;
import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.DatabaseNode;


/**
 * Событие успешной загрузки слепков и их парсинга в {@link AbstractNode}
 * @author kakeru
 *
 */
public class SnapshotsUploadedEvent
        extends GwtEvent<SnapshotsUploadedEventHandler>
{
    public static final Type<SnapshotsUploadedEventHandler> TYPE = 
            new Type<SnapshotsUploadedEventHandler>();
    
    private final DatabaseNode source;
    private final DatabaseNode destination;

    public SnapshotsUploadedEvent(DatabaseNode source, DatabaseNode destination)
    {
        super();
        this.source = source;
        this.destination = destination;
    }

    public DatabaseNode getSource()
    {
        return source;
    }

    public DatabaseNode getDestination()
    {
        return destination;
    }

    @Override
    public Type<SnapshotsUploadedEventHandler> getAssociatedType()
    {
        return TYPE;
    }

    @Override
    protected void dispatch(SnapshotsUploadedEventHandler handler)
    {
        handler.onSnapshotsUploaded(this);
    }
    
    
}
