package com.otoil.dbcomparator.client;


import org.fusesource.restygwt.client.MethodCallback;

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
    private SnapshotParserService snapshotParser;
    
    public SnapshotsModelZipRestImpl()
    {
        snapshotParser = GWT.create(SnapshotParserService.class);
    }
    
    @Override
    public void parseSnapshot(String id, MethodCallback<DatabaseNode> callback)
    {
        snapshotParser.parseSnapshot(id, callback);
    }

}
