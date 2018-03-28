package com.otoil.dbcomparator.client.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.DatabaseNode;

/**
 * Модель, осуществляющая выгрузку данных из переданных
 * слепков в {@link AbstractNode}
 * @author kakeru
 */
public interface SnapshotsModel
{
    void parseSnapshot(String id, AsyncCallback<DatabaseNode> callback);
}