package com.otoil.dbcomparator.shared.beans.containers;


import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.otoil.dbcomparator.client.resources.DBComparatorTemplates;
import com.otoil.dbcomparator.client.resources.internationalization.DBComparatorMessages;
import com.otoil.dbcomparator.shared.beans.AbstractNode;
import com.otoil.dbcomparator.shared.beans.AbstractNode.NodeState;


/**
 * Контейнер для других узлов (например, columns для столбцов, constraints для
 * ограничений и так далее).
 * 
 * @author kakeru
 */
public abstract class ContainerNode extends AbstractNode implements Cloneable
{
    private String subelementsType = "";

    public ContainerNode()
    {
        this(null, null);
    }

    public ContainerNode(String name, String subelementsType)
    {
        super(name);
        this.subelementsType = subelementsType;
    }

    public String getSubelementsType()
    {
        return subelementsType;
    }

    public void setSubelementsType(String containerFor)
    {
        this.subelementsType = containerFor;
    }

    protected void appendInfo(StringBuilder builder,
        Function<String, SafeHtml> template, int value, String label,
        boolean writeComma)
    {
        // do not print if value is 0
        if (value > 0)
        {
            StringBuilder temp = new StringBuilder();
            temp.append(Integer.toString(value));
            temp.append(" ");
            temp.append(label);

            if (template != null)
            {
                String formatted = template.apply(temp.toString()).asString();
                temp = new StringBuilder(formatted);
            }

            builder.append(temp.toString());

            if (writeComma)
            {
                builder.append(", ");
            }
        }
    }

    @Override
    protected String generateChangesSummary(String prefix,
        DBComparatorMessages localizer, DBComparatorTemplates templates)
    {
        int changed = 0;
        int nonChanged = 0;
        int deleted = 0;
        int added = 0;
        List<AbstractNode> children = getChildren();
        int total = children.size();

        Iterator<AbstractNode> it = children.iterator();
        while (it.hasNext())
        {
            AbstractNode node = it.next();
            switch (node.getState())
            {
                case NON_CHANGED:
                    nonChanged++;
                    break;
                case CHANGED:
                    changed++;
                    break;
                case DELETED:
                    deleted++;
                    break;
                case ADDED:
                    added++;
                    break;
            }
        }

        StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder.append(prefix);

        appendInfo(summaryBuilder, templates::summaryNonChanged, nonChanged,
            localizer.summaryNonChanged(),
            changed != 0 || deleted != 0 || added != 0 || total != 0);

        appendInfo(summaryBuilder, templates::summaryChanged, changed,
            localizer.summaryChanged(),
            deleted != 0 || added != 0 || total != 0);

        appendInfo(summaryBuilder, templates::summaryDeleted, deleted,
            localizer.summaryDeleted(), added != 0 || total != 0);

        appendInfo(summaryBuilder, templates::summaryAdded, added,
            localizer.summaryAdded(), total != 0);

        appendInfo(summaryBuilder, null, total, localizer.summaryTotal(),
            false);

        return summaryBuilder.toString();
    }

    @Override
    public void setName(String name)
    {
    }

    public abstract ContainerNode clone();
}
