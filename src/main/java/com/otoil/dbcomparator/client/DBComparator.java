package com.otoil.dbcomparator.client;


import org.fusesource.restygwt.client.Defaults;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.otoil.dbcomparator.client.interfaces.ClientFactory;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DBComparator implements EntryPoint
{

    @Override
    public void onModuleLoad()
    {
        Defaults.setServiceRoot(GWT.getModuleBaseURL() + "rest");
        
        ClientFactory clientFactory = new ClientFactoryDesktopSnapshotsImpl();
        SnapshotsPresenter snapshotsPresenter = new SnapshotsPresenter(
            clientFactory.getDBSourcesView(),
            clientFactory.getSnapshotsModel(),
            clientFactory.getNameGenerator());
        
        ResultPresenter resultPresenter = new ResultPresenter(
            new ResultViewDesktopImpl(), new ComparingModelRestImpl());
        
        // вообще говоря, может быть, нужно было использовать всякие
        // ActivityManager'ы, историю и т. д.
        // спросить у Константина, что со всем этим добром делать
        
        VerticalPanel mainPanel = new VerticalPanel();
        SimplePanel topPanel = new SimplePanel();
        SimplePanel centerPanel = new SimplePanel();
        
        // без этого высота панельки всегда будет 0, и мы ничего не увидим :(
        // TODO: css-styling
        centerPanel.setHeight("400px");
        mainPanel.add(topPanel);
        mainPanel.add(centerPanel);
        RootLayoutPanel.get().add(mainPanel);
        snapshotsPresenter.start(topPanel, clientFactory.getEventBus());
        resultPresenter.start(centerPanel, clientFactory.getEventBus());
        
        // hence presenter.onStop() is never called....
    }
    
}

