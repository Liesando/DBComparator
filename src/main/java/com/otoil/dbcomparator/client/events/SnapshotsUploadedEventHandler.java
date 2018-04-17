package com.otoil.dbcomparator.client.events;


import com.google.gwt.event.shared.EventHandler;


/**
 * Хендлер для {@link SnapshotsUploadedEvent}
 * 
 * @author Sergey Medelyan
 */
@FunctionalInterface
public interface SnapshotsUploadedEventHandler extends EventHandler
{
    void onSnapshotsUploaded(SnapshotsUploadedEvent event);
}
