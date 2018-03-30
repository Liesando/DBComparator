package com.otoil.dbcomparator.client;


import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.google.gwt.core.client.GWT;
import com.otoil.dbcomparator.client.interfaces.ComparingModel;
import com.otoil.dbcomparator.client.interfaces.SnapshotComparatorService;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.ComparisonRequest;
import com.otoil.dbcomparator.shared.ComparisonResult;
import com.otoil.dbcomparator.shared.DatabaseNode;


/**
 * Модель, сравнивающая слепки с использованием REST-сервиса (сервера?)
 * 
 * @author kakeru
 */
public class ComparingModelRestImpl implements ComparingModel
{
    private SnapshotComparatorService service;

    public ComparingModelRestImpl()
    {
        service = GWT.create(SnapshotComparatorService.class);
    }

    @Override
    public void compare(DatabaseNode sourceRoot, DatabaseNode destRoot,
        MethodCallback<ComparisonResult> callback)
    {
        REST.withCallback(callback).call(service)
            .compareSnapshot(new ComparisonRequest(sourceRoot, destRoot));
    }

}
