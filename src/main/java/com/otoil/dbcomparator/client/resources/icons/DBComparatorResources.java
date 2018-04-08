package com.otoil.dbcomparator.client.resources.icons;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface DBComparatorResources extends ClientBundle
{
    public static final DBComparatorResources INSTANCE = GWT.create(DBComparatorResources.class);
    
    @Source("icons.css")
    DefaultCssResource css();
    
    @Source("table.png")
    ImageResource tableImage();
}