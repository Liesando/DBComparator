package com.otoil.dbcomparator.client.events;


import com.google.gwt.event.shared.GwtEvent;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.services.ComparisonBean;

import lombok.Data;


/**
 * Событие успешной загрузки слепков и их парсинга в {@link AbstractNode}
 * 
 * @author Sergey Medelyan
 */
@Data
public class SnapshotsUploadedEvent
        extends GwtEvent<SnapshotsUploadedEventHandler>
{
    public static final Type<SnapshotsUploadedEventHandler> TYPE = new Type<SnapshotsUploadedEventHandler>();

    private final ComparisonBean comparisonBean;

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
