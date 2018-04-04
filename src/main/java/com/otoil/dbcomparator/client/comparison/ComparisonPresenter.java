package com.otoil.dbcomparator.client.comparison;


import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.otoil.dbcomparator.client.events.SnapshotsUploadedEvent;
import com.otoil.dbcomparator.client.events.SnapshotsUploadedEventHandler;
import com.otoil.dbcomparator.client.main.ClientFactory;
import com.otoil.dbcomparator.shared.services.ComparisonResult;


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
                    model.compare(event.getSource(), event.getDestination(),
                        new MethodCallback<ComparisonResult>()
                        {

                            @Override
                            public void onSuccess(Method method,
                                ComparisonResult response)
                            {
                                view.setSourceDBRoot(response.getSourceRoot());
                                view.setDestinationDBRoot(response.getDestRoot());
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

        panel.setWidget(view);
    }

}