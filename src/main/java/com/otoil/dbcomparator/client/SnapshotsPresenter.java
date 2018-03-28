package com.otoil.dbcomparator.client;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.otoil.dbcomparator.client.interfaces.SnapshotsModel;
import com.otoil.dbcomparator.client.interfaces.DBSourcesView;
import com.otoil.dbcomparator.client.interfaces.UniqueNameGenerator;
import com.otoil.dbcomparator.shared.DatabaseNode;


/**
 * Презентер для загрузки слепков
 * 
 * @author kakeru
 */
public class SnapshotsPresenter extends AbstractActivity
{
    private DBSourcesView view;
    private SnapshotsModel model;
    private UniqueNameGenerator nameGenerator;
    private DatabaseNode source;
    private DatabaseNode destination;
    private HandlerRegistration submitHandlerReg;

    public SnapshotsPresenter(DBSourcesView view, SnapshotsModel model,
        UniqueNameGenerator nameGenerator)
    {
        super();
        this.view = view;
        this.model = model;
        this.nameGenerator = nameGenerator;
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
                new AsyncCallback<DatabaseNode>()
                {
                    @Override
                    public void onSuccess(DatabaseNode result)
                    {
                        source = result;
                        fireEvent(eventBus);
                    }

                    @Override
                    public void onFailure(Throwable caught)
                    {
                        // TODO: обработать ошибку
                    }
                });

            model.parseSnapshot(view.destination().getName(),
                new AsyncCallback<DatabaseNode>()
                {

                    @Override
                    public void onSuccess(DatabaseNode result)
                    {
                        destination = result;
                        fireEvent(eventBus);
                    }

                    @Override
                    public void onFailure(Throwable caught)
                    {
                        // TODO: обработать ошибку
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
