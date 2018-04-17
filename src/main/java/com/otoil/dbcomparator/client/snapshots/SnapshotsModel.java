package com.otoil.dbcomparator.client.snapshots;


import org.fusesource.restygwt.client.MethodCallback;

import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;


/**
 * Модель, осуществляющая выгрузку данных из переданных слепков в
 * {@link AbstractNode}
 * 
 * @author Sergey Medelyan
 */
public interface SnapshotsModel
{
    void parseSnapshot(String id, MethodCallback<DatabaseNode> callback);
}