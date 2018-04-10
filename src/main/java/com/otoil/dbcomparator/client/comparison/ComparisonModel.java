package com.otoil.dbcomparator.client.comparison;


import org.fusesource.restygwt.client.MethodCallback;

import com.otoil.dbcomparator.shared.services.ComparisonBean;


/**
 * Модель в MVP, осуществляющая сравнение двух слепков
 * 
 * @author kakeru
 */
public interface ComparisonModel
{
    void compare(ComparisonBean comparisonRequest,
        MethodCallback<ComparisonBean> callback);
}