package com.otoil.dbcomparator.client.comparison;


import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.otoil.dbcomparator.client.events.SnapshotsUploadedEvent;
import com.otoil.dbcomparator.client.events.SnapshotsUploadedEventHandler;
import com.otoil.dbcomparator.client.events.TreeNodeSelectedEvent;
import com.otoil.dbcomparator.client.main.ClientFactory;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.services.ComparisonBean;


/**
 * Презентер для результатов сравнения
 * 
 * @author kakeru
 */
public class ComparisonPresenter extends AbstractActivity
{
    private ComparisonView view;
    private ComparisonModel model;

    public ComparisonPresenter(ClientFactory factory)
    {
        super();
        view = factory.getComparisonView();
        model = factory.getComparisonModel();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus)
    {
        eventBus.addHandler(SnapshotsUploadedEvent.TYPE,
            new SnapshotsUploadedEventHandler()
            {

                @Override
                public void onSnapshotsUploaded(SnapshotsUploadedEvent event)
                {
                    model.compare(event.getComparisonBean(),
                        new MethodCallback<ComparisonBean>()
                        {

                            @Override
                            public void onSuccess(Method method,
                                ComparisonBean response)
                            {
                                view.setSourceDBRoot(response.getSource());
                                view.setDestinationDBRoot(
                                    response.getDest());
                            }

                            @Override
                            public void onFailure(Method method,
                                Throwable exception)
                            {
                                // TODO Auto-generated method stub

                            }
                        });
                }
            });

        view.addSourceTreeSelectionChangedHandler((model, event) -> {
            fireEvent(model, event, eventBus);
        });

        view.addDestTreeSelectionChangedHandler((model, event) -> {
            fireEvent(model, event, eventBus);
        });

        panel.setWidget(view);
    }

    private void fireEvent(SingleSelectionModel<AbstractNode> model,
        SelectionChangeEvent event, EventBus eventBus)
    {
        TreeNodeSelectedEvent e = new TreeNodeSelectedEvent();
        e.setSelectedNode(model.getSelectedObject());
        eventBus.fireEvent(e);
    }

}