package com.otoil.dbcomparator.client.comparison;


import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.google.gwt.core.client.GWT;
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.services.ComparisonBean;
import com.otoil.dbcomparator.shared.services.ComparisonService;


/**
 * Модель, сравнивающая слепки с использованием REST-сервиса (сервера?)
 * 
 * @author Sergey Medelyan
 */
public class ComparisonModelRestImpl implements ComparisonModel
{
    private ComparisonService service;
    private final DBComparatorMessages messages = GWT
        .create(DBComparatorMessages.class);

    public ComparisonModelRestImpl()
    {
        service = GWT.create(ComparisonService.class);
    }

    @Override
    public void compare(ComparisonBean comparisonRequest,
        MethodCallback<ComparisonBean> callback)
    {
        comparisonRequest.getSource().setOfSourceSnapshot(true);
        comparisonRequest.getSource().setName(messages.oldDbName());
        comparisonRequest.getDest().setName(messages.latetsDbName());
        REST.withCallback(callback).call(service)
            .compareSnapshot(comparisonRequest);
    }

}
