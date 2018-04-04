package com.otoil.dbcomparator.client.comparison;


import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.google.gwt.core.client.GWT;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.services.ComparisonRequest;
import com.otoil.dbcomparator.shared.services.ComparisonResult;
import com.otoil.dbcomparator.shared.services.ComparisonService;


/**
 * Модель, сравнивающая слепки с использованием REST-сервиса (сервера?)
 * 
 * @author kakeru
 */
public class ComparisonModelRestImpl implements ComparisonModel
{
    private ComparisonService service;

    public ComparisonModelRestImpl()
    {
        service = GWT.create(ComparisonService.class);
    }

    @Override
    public void compare(DatabaseNode sourceRoot, DatabaseNode destRoot,
        MethodCallback<ComparisonResult> callback)
    {
        sourceRoot.setOfSourceSnapshot(true);
        REST.withCallback(callback).call(service)
            .compareSnapshot(new ComparisonRequest(sourceRoot, destRoot));
    }

}
