package com.otoil.dbcomparator.client.interfaces;


import org.fusesource.restygwt.client.MethodCallback;

import com.otoil.dbcomparator.shared.AbstractNode;
import com.otoil.dbcomparator.shared.DatabaseNode;


/**
 * Модель, осуществляющая выгрузку данных из переданных слепков в
 * {@link AbstractNode}
 * 
 * @author kakeru
 */
public interface SnapshotsModel
{
    void parseSnapshot(String id, MethodCallback<DatabaseNode> callback);
}