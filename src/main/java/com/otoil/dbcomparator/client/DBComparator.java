package com.otoil.dbcomparator.client;


import org.fusesource.restygwt.client.Defaults;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.otoil.dbcomparator.client.main.ClientFactory;
import com.otoil.dbcomparator.client.main.ClientFactoryDesktopSnapshotsImpl;
import com.otoil.dbcomparator.client.main.MainPresenter;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Sergey Medelyan
 */
public class DBComparator implements EntryPoint
{

    @Override
    public void onModuleLoad()
    {
        Defaults.setServiceRoot(GWT.getModuleBaseURL() + "rest");
        ClientFactory clientFactory = new ClientFactoryDesktopSnapshotsImpl();
        MainPresenter mainPresenter = new MainPresenter(clientFactory);
        SimplePanel root = new SimplePanel();
        RootLayoutPanel.get().add(root);
        mainPresenter.start(root, clientFactory.getEventBus());
    }

}
