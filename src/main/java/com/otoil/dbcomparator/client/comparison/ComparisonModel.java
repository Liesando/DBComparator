package com.otoil.dbcomparator.client.comparison;

import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.services.ComparisonResult;

/**
 * Модель в MVP, осуществляющая сравнение двух слепков
 * 
 * @author kakeru
 *
 */
public interface ComparisonModel
{
    void compare(DatabaseNode sourceRoot, DatabaseNode destRoot,
        MethodCallback<ComparisonResult> callback);
}