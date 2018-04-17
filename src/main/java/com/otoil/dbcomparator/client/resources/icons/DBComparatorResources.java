package com.otoil.dbcomparator.client.resources.icons;


import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;


public interface DBComparatorResources extends ClientBundle
{
    public static final DBComparatorResources INSTANCE = GWT
        .create(DBComparatorResources.class);

    @Source("icons.css")
    DefaultCssResource css();

    @Source("table.png")
    ImageResource tableImage();

    @Source("columntypeicons/blob_16x16.png")
    ImageResource colBlobImage();

    @Source("columntypeicons/boolean_16x16.png")
    ImageResource colBooleanImage();

    @Source("columntypeicons/clob_16x16.png")
    ImageResource colClobImage();

    @Source("columntypeicons/date_16x16.png")
    ImageResource colDateImage();

    @Source("columntypeicons/number_16x16.png")
    ImageResource colNumberImage();

    @Source("columntypeicons/string_16x16.png")
    ImageResource colStringImage();
}