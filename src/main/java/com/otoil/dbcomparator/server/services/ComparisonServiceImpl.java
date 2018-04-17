package com.otoil.dbcomparator.server.services;


import javax.ws.rs.Path;

import com.otoil.dbcomparator.server.comparison.DBColumnComparator;
import com.otoil.dbcomparator.server.comparison.DBContainerComparator;
import com.otoil.dbcomparator.server.comparison.DBPrimaryConstraintComparator;
import com.otoil.dbcomparator.server.comparison.DBRootComparator;
import com.otoil.dbcomparator.server.comparison.DBTableComparator;
import com.otoil.dbcomparator.shared.beans.DatabaseNode;
import com.otoil.dbcomparator.shared.beans.TableNode;
import com.otoil.dbcomparator.shared.beans.containers.ColumnsContainerNode;
import com.otoil.dbcomparator.shared.beans.containers.ConstraintsContainerNode;
import com.otoil.dbcomparator.shared.beans.containers.TablesContainerNode;
import com.otoil.dbcomparator.shared.services.ComparisonBean;
import com.otoil.dbcomparator.shared.services.ComparisonService;


/**
 * Сервис, осуществляющий сравнение слепков
 * 
 * @author Sergey Medelyan
 */
@Path("snapshot-comparator")
public class ComparisonServiceImpl implements ComparisonService
{

    @Override
    public ComparisonBean compareSnapshot(ComparisonBean request)
    {
        DBRootComparator rootComparator = createRootComparator();

        rootComparator.performComparison(request.getSource(),
            request.getDest());
        rootComparator.performComparison(request.getDest(),
            request.getSource());

        return new ComparisonBean(request.getSource(), request.getDest());
    }

    private DBRootComparator createRootComparator()
    {
        DBRootComparator root = new DBRootComparator();
        DBContainerComparator<TablesContainerNode, DatabaseNode> tablesContainerComparator = new DBContainerComparator<TablesContainerNode, DatabaseNode>(
            TablesContainerNode.class, DatabaseNode.class);

        DBTableComparator tableComparator = new DBTableComparator();
        DBContainerComparator<ColumnsContainerNode, TableNode> columnsContainerComparator = new DBContainerComparator<ColumnsContainerNode, TableNode>(
            ColumnsContainerNode.class, TableNode.class);
        DBContainerComparator<ConstraintsContainerNode, TableNode> constraintsContainerComparator = new DBContainerComparator<ConstraintsContainerNode, TableNode>(
            ConstraintsContainerNode.class, TableNode.class);

        DBColumnComparator columnComparator = new DBColumnComparator();
        DBPrimaryConstraintComparator primaryConstraintComparator = new DBPrimaryConstraintComparator();

        root.registerSubcomparator(tablesContainerComparator);
        tablesContainerComparator.registerSubcomparator(tableComparator);
        tableComparator.registerSubcomparator(columnsContainerComparator);
        tableComparator.registerSubcomparator(constraintsContainerComparator);
        columnsContainerComparator.registerSubcomparator(columnComparator);
        constraintsContainerComparator
            .registerSubcomparator(primaryConstraintComparator);

        return root;
    }
}
