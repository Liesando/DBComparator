package com.otoil.dbcomparator.client.details;


import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.otoil.dbcomparator.client.events.TreeNodeSelectedEvent;
import com.otoil.dbcomparator.client.main.ClientFactory;


/**
 * Презентер секции информации о выбранном узле
 * 
 * @author Sergey Medelyan
 */
public class DetailsPresenter extends AbstractActivity
{
    private DetailsView detailsView;

    public DetailsPresenter(ClientFactory factory)
    {
        detailsView = factory.getDetailsView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus)
    {
        eventBus.addHandler(TreeNodeSelectedEvent.TYPE, e -> {
            detailsView.displayNodeDetails(e.getSelectedNode());
        });

        panel.setWidget(detailsView);
    }
}
