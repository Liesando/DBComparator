package com.otoil.dbcomparator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.otoil.dbcomparator.client.interfaces.ComparingModel;
import com.otoil.dbcomparator.shared.AbstractNode.NodeState;
import com.otoil.dbcomparator.shared.ComparisonResult;
import com.otoil.dbcomparator.shared.DatabaseNode;


/**
 * Модель, сравнивающая слепки с использованием REST-сервиса (сервера?)
 * @author kakeru
 *
 */
public class RestComparingModel implements ComparingModel
{

    @Override
    public void compare(DatabaseNode sourceRoot, DatabaseNode destRoot,
        AsyncCallback<ComparisonResult> callback)
    {
        // mocking
        sourceRoot.getChildren().get(0).setState(NodeState.CHANGED);
        sourceRoot.getChildren().get(1).setState(NodeState.DELETED);
        destRoot.getChildren().get(0).setState(NodeState.ADDED);
        destRoot.getChildren().get(1).setState(NodeState.CHANGED);
        destRoot.getChildren().get(1).getChildren().get(0).setState(NodeState.CHANGED);
        destRoot.getChildren().get(1).getChildren().get(1).setState(NodeState.CHANGED);
        ComparisonResult result = new ComparisonResult(sourceRoot, destRoot);
        callback.onSuccess(result);
    }
    
}
