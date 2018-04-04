package com.otoil.dbcomparator.client.snapshots;


import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.otoil.dbcomparator.client.events.SnapshotsUploadedEvent;
import com.otoil.dbcomparator.client.main.ClientFactory;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;


/**
 * Презентер для загрузки слепков
 * 
 * @author kakeru
 */
public class SnapshotsPresenter extends AbstractActivity
{
    private SnapshotsView view;
    private SnapshotsModel model;
    private SnapshotsUniqueNameGenerator nameGenerator;
    private DatabaseNode source;
    private DatabaseNode destination;
    private HandlerRegistration submitHandlerReg;

    public SnapshotsPresenter(ClientFactory factory)
    {
        super();
        view = factory.getSnapshotsView();
        model = factory.getSnapshotsModel();
        nameGenerator = factory.getSnapshotsUniqueNameGenerator();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus)
    {
        // задать имена двум аплоудерам
        view.source().setName(nameGenerator.generateUniqueName());
        view.destination().setName(nameGenerator.generateUniqueName());

        // зарегать хендлер для сабмита
        submitHandlerReg = view.addSubmitCompleteHandler(submitEvent -> {
            model.parseSnapshot(view.source().getName(),
                new MethodCallback<DatabaseNode>()
                {

                    @Override
                    public void onFailure(Method method, Throwable exception)
                    {
                        throw new RuntimeException(exception);
                    }

                    @Override
                    public void onSuccess(Method method, DatabaseNode response)
                    {
                        source = response;
                        fireEvent(eventBus);

                    }
                });

            model.parseSnapshot(view.destination().getName(),
                new MethodCallback<DatabaseNode>()
                {

                    @Override
                    public void onFailure(Method method, Throwable exception)
                    {
                        throw new RuntimeException(exception);
                    }

                    @Override
                    public void onSuccess(Method method, DatabaseNode response)
                    {
                        destination = response;
                        fireEvent(eventBus);

                    }
                });
        });

        // отобразить нашу вьюшку
        panel.setWidget(view);
    }

    private void fireEvent(EventBus eventBus)
    {
        // бросить эвент с наполненными данными
        if (source != null && destination != null)
        {
            SnapshotsUploadedEvent event = new SnapshotsUploadedEvent(source,
                destination);
            eventBus.fireEvent(event);
        }
    }

    @Override
    public void onStop()
    {
        submitHandlerReg.removeHandler();
    }

}
