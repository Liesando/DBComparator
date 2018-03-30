package com.otoil.dbcomparator.client;


import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.google.gwt.core.client.GWT;
import com.otoil.dbcomparator.client.interfaces.SnapshotParserService;
import com.otoil.dbcomparator.client.interfaces.SnapshotsModel;
import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.ColumnNode;
import com.otoil.dbcomparator.shared.DatabaseNode;
import com.otoil.dbcomparator.shared.TableNode;


/**
 * Модель для парсинга слепков в {@link AbstractNode}
 * 
 * @author kakeru
 */
public class SnapshotsModelZipRestImpl implements SnapshotsModel
{
    private SnapshotParserService snapshotParserService;
    
    public SnapshotsModelZipRestImpl()
    {
        snapshotParserService = GWT.create(SnapshotParserService.class);
    }
    
    @Override
    public void parseSnapshot(String snapshotId, MethodCallback<DatabaseNode> callback)
    {
        REST.withCallback(callback).call(snapshotParserService).parseSnapshot(snapshotId);
    }

}
