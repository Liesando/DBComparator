package com.otoil.dbcomparator.client.details;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.otoil.dbcomparator.client.resources.DBComparatorTemplates;
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.ColumnNode;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;


public class DetailsViewDesktopImpl implements DetailsView
{
    private static final String SPLIT_PANEL_HEIGHT = "200px";
    private static final double TEXT_AREA_SIZE = 320.0;

    private SplitLayoutPanel splitPanel = new SplitLayoutPanel();
    private RichTextArea sourceTextArea = new RichTextArea();
    private RichTextArea destTextArea = new RichTextArea();

    private DBComparatorMessages messages = GWT
        .create(DBComparatorMessages.class);
    private DBComparatorTemplates templates = GWT
        .create(DBComparatorTemplates.class);

    public DetailsViewDesktopImpl()
    {
        splitPanel.addWest(sourceTextArea, TEXT_AREA_SIZE);
        splitPanel.add(destTextArea);
        splitPanel.setWidth("100%");
        splitPanel.setHeight("100%");

        sourceTextArea.setEnabled(false);
        sourceTextArea.setWidth("100%");
        sourceTextArea.setHeight("100%");
        destTextArea.setEnabled(false);
        destTextArea.setWidth("100%");
        destTextArea.setHeight("100%");
    }

    @Override
    public Widget asWidget()
    {
        return splitPanel;
    }

    @Override
    public <T extends AbstractNode> void displayNodeDetails(T node)
    {
        StringBuilder detailsBuilder = new StringBuilder();

        // type first
        compose(detailsBuilder, typeOf(node));
        
        // then name
        compose(detailsBuilder, messages.name(node.getName()));
        
        // then comment
        compose(detailsBuilder, messages.commentary(node.getCommentary()));
        
        // then status
        compose(detailsBuilder, statusOf(node));

        // specifics
        if (node instanceof TableNode)
        {
            TableNode table = (TableNode) node;
            compose(detailsBuilder, messages.tableOwner(table.getOwner()));
            compose(detailsBuilder,
                messages.tableTablespace(table.getTablespace()));
            compose(detailsBuilder,
                messages.tableTemporary(toStr(table.isTemporary())));
            compose(detailsBuilder,
                messages.tableOfIoTType(toStr(table.isOfIotType())));
        }
        else if (node instanceof ColumnNode)
        {
            ColumnNode column = (ColumnNode) node;
            compose(detailsBuilder, messages.columnType(column.getType()));
            compose(detailsBuilder,
                messages.columnNullable(toStr(column.isNullable())));
            compose(detailsBuilder,
                messages.columnVirtual(toStr(column.isVirtual())));
        }
        // TODO: other node types

        // summary (if any)
        String summary = node.getChangesSummary();
        if(summary != null)
        {
            compose(detailsBuilder, messages.summary(summary));
        }
        
        // print
        if (node.isOfSourceSnapshot())
        {
            sourceTextArea.setHTML(detailsBuilder.toString());
        }
        else
        {
            destTextArea.setHTML(detailsBuilder.toString());
        }
    }

    private void compose(StringBuilder builder, String text)
    {
        builder.append(text);
        builder.append("<br>");
    }

    private <T extends AbstractNode> String typeOf(T node)
    {
        String type = messages.withoutAType();
        if (node instanceof DatabaseNode)
        {
            type = messages.database();
        }
        else if (node instanceof TableNode)
        {
            type = messages.table();
        }
        else if (node instanceof ColumnNode)
        {
            type = messages.column();
        }

        return templates.typeOfItem(type).asString();
    }

    private <T extends AbstractNode> String statusOf(T node)
    {
        switch (node.getState())
        {
            case NON_CHANGED:
                return messages.status(messages.nonChanged());
            case CHANGED:
                return templates.changed(messages.status(messages.changed()))
                    .asString();
            case DELETED:
                return templates.deleted(messages.status(messages.deleted()))
                    .asString();
            case ADDED:
                return templates.added(messages.status(messages.added()))
                    .asString();
        }

        // unreacheable
        return "";
    }

    private String toStr(boolean value)
    {
        return value ? messages.yes() : messages.no();
    }

}
