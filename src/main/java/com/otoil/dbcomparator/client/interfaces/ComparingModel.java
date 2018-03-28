package com.otoil.dbcomparator.client.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.otoil.dbcomparator.shared.ComparisonResult;
import com.otoil.dbcomparator.shared.DatabaseNode;

/**
 * Модель в MVP, осуществляющая сравнение двух слепков
 * 
 * @author kakeru
 *
 */
public interface ComparingModel
{
    void compare(DatabaseNode sourceRoot, DatabaseNode destRoot,
        AsyncCallback<ComparisonResult> callback);
}