package com.otoil.dbcomparator.client.snapshots;


import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.google.gwt.core.client.GWT;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.ColumnNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;
import com.otoil.dbcomparator.shared.services.SnapshotsParserService;


/**
 * Модель для парсинга слепков в {@link AbstractNode}
 * 
 * @author kakeru
 */
public class SnapshotsModelZipRestImpl implements SnapshotsModel
{
    private SnapshotsParserService snapshotParserService;
    
    public SnapshotsModelZipRestImpl()
    {
        snapshotParserService = GWT.create(SnapshotsParserService.class);
    }
    
    @Override
    public void parseSnapshot(String snapshotId, MethodCallback<DatabaseNode> callback)
    {
        REST.withCallback(callback).call(snapshotParserService).parseSnapshot(snapshotId);
    }

}
