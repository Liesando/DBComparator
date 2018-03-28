package com.otoil.dbcomparator.client;

import com.google.gwt.event.shared.EventHandler;

/**
 * Хендлер для {@link SnapshotsUploadedEvent}
 * @author kakeru
 *
 */
public interface SnapshotsUploadedEventHandler extends EventHandler
{
    void onSnapshotsUploaded(SnapshotsUploadedEvent event);
}
