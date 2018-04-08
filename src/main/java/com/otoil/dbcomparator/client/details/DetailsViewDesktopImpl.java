package com.otoil.dbcomparator.client.details;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RichTextArea;
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
    private static final int DETAILS_AREA_LINES = 8;
    private RichTextArea textArea = new RichTextArea();
    private DBComparatorMessages messages = GWT
        .create(DBComparatorMessages.class);
    private DBComparatorTemplates templates = GWT
        .create(DBComparatorTemplates.class);

    public DetailsViewDesktopImpl()
    {
        textArea.setEnabled(false);
        ;
        // textArea.seth(DETAILS_AREA_LINES);
        textArea.setWidth("100%");
    }

    @Override
    public Widget asWidget()
    {
        return textArea;
    }

    @Override
    public <T extends AbstractNode> void displayNodeDetails(T node)
    {
        StringBuilder detailsBuilder = new StringBuilder();

        compose(detailsBuilder, typeOf(node));
        compose(detailsBuilder, messages.name(node.getName()));
        compose(detailsBuilder, messages.commentary(node.getCommentary()));
        compose(detailsBuilder, statusOf(node));

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

        textArea.setHTML(detailsBuilder.toString());
    }

    private void compose(StringBuilder builder, String text)
    {
        builder.append(text + "<br>");
    }

    private <T extends AbstractNode> String typeOf(T node)
    {
        if (node instanceof DatabaseNode)
        {
            return messages.database();
        }
        else if (node instanceof TableNode)
        {
            return messages.table();
        }
        else if (node instanceof ColumnNode)
        {
            return messages.column();
        }

        return messages.withoutAType();
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
                return templates.added(messages.status(messages.added())).asString();
        }

        // unreacheable
        return "";
    }

    private String toStr(boolean value)
    {
        return value ? messages.yes() : messages.no();
    }

}
