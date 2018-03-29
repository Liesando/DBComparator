package com.otoil.dbcomparator.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.otoil.dbcomparator.client.interfaces.DBSourcesView;


/**
 * Вьюха загрузки слепков из .zip-архивов
 * @author kakeru
 *
 */
public class DBSourcesViewZipSnapshotsImpl implements DBSourcesView
{
    private final FormPanel form = new FormPanel();
    private final FileUpload source = new FileUpload();
    private final FileUpload destination = new FileUpload();
    private final Button compareBtn = new Button();

    public DBSourcesViewZipSnapshotsImpl()
    {
        compareBtn.setText("Compare");
        compareBtn.addClickHandler(event -> {
           form.submit(); 
        });
        form.setAction(GWT.getModuleBaseURL() + "upload");
        form.setMethod(FormPanel.METHOD_POST);
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        HorizontalPanel panel = new HorizontalPanel();
        form.add(panel);
        panel.add(source);
        panel.add(destination);
        panel.add(compareBtn);

        form.addSubmitHandler(event -> {
            if (!validateSubmit())
            {
                event.cancel();
            }
        });
    }

    private boolean validateSubmit()
    {
        return source.getFilename().length() > 0
                && destination.getFilename().length() > 0;
    }
    
    @Override
    public Widget asWidget()
    {
        return form;
    }


    @Override
    public HandlerRegistration addSubmitCompleteHandler(
        SubmitCompleteHandler handler)
    {
        return form.addSubmitCompleteHandler(handler);
    }

    @Override
    public HasName source()
    {
        return source;
    }

    @Override
    public HasName destination()
    {
        return destination;
    }

}
