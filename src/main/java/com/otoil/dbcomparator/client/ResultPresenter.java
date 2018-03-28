package com.otoil.dbcomparator.client;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.otoil.dbcomparator.client.interfaces.ComparingModel;
import com.otoil.dbcomparator.client.interfaces.ResultView;
import com.otoil.dbcomparator.shared.ComparisonResult;


/**
 * Презентер для результатов сравнения
 * @author kakeru
 *
 */
public class ResultPresenter extends AbstractActivity
{
    private ResultView view;
    private ComparingModel model;

    public ResultPresenter(ResultView view, ComparingModel model)
    {
        super();
        this.view = view;
        this.model = model;
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
                        new AsyncCallback<ComparisonResult>()
                        {
                            @Override
                            public void onFailure(Throwable caught)
                            {
                                // TODO: обработать ошибку
                            }

                            @Override
                            public void onSuccess(ComparisonResult result)
                            {
                                view.setSourceDBRoot(result.getSourceRoot());
                                view.setDestinationDBRoot(result.getDestRoot());
                            }
                        });
                }
            });
        
        panel.setWidget(view);
    }

}