package com.otoil.dbcomparator.client.resources;

import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;

public interface DBComparatorTemplates extends SafeHtmlTemplates
{
    @Template("<span style=\"color: blue;\"><b>{0}</b></span>")
    SafeHtml changed(String statusContent);
    
    @Template("<span style=\"color: red;\">{0}</span>")
    SafeHtml deleted(String statusContent);
    
    @Template("<span style=\"color: green;\">{0}</span>")
    SafeHtml added(String statusContent);
    
    @Template("<span class=\"{0}\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>")
    SafeHtml treeItemIcon(String iconClass);
    
    @Template("<span class=\"{0}\">{1}</span>")
    SafeHtml treeItem(String cssClass, String name);
    
    @Template("<span class=\"db-commentary\">&nbsp;&nbsp;<i>- {0}</i></span>")
    SafeHtml treeItemComment(String comment);
}