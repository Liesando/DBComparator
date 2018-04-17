package com.otoil.dbcomparator.client.resources.internationalization;


import com.google.gwt.i18n.client.Messages;


public interface DBComparatorMessages extends Messages
{
    // guis
    @DefaultMessage("Compare")
    String compare();

    @DefaultMessage("oldDB")
    String oldDbName();

    @DefaultMessage("latestDB")
    String latetsDbName();

    // types
    @DefaultMessage("Database")
    String database();

    @DefaultMessage("Table")
    String table();

    @DefaultMessage("Tables")
    String tables();

    @DefaultMessage("Column")
    String column();

    @DefaultMessage("Columns")
    String columns();

    @DefaultMessage("Constraints")
    String constraints();

    @DefaultMessage("without a type")
    String withoutAType();

    // common
    @DefaultMessage("Comment: {0}")
    String commentary(String comment);

    @DefaultMessage("Name: {0}")
    String name(String name);

    @DefaultMessage("Status: {0}")
    String status(String status);

    @DefaultMessage("<br>Summary:<br>{0}")
    String summary(String text);

    @DefaultMessage("non-changed")
    String summaryNonChanged();

    @DefaultMessage("changed")
    String summaryChanged();

    @DefaultMessage("deleted")
    String summaryDeleted();

    @DefaultMessage("added")
    String summaryAdded();

    @DefaultMessage("total")
    String summaryTotal();

    @DefaultMessage("non-changed")
    String nonChanged();

    @DefaultMessage("changed")
    String changed();

    @DefaultMessage("object will be deleted in the latest database version")
    String deleted();

    @DefaultMessage("object will be added in the latest database version")
    String added();

    @DefaultMessage("yes")
    String yes();

    @DefaultMessage("no")
    String no();

    // table
    @DefaultMessage("Owner: {0}")
    String tableOwner(String owner);

    @DefaultMessage("Tablespace: {0}")
    String tableTablespace(String tablespace);

    @DefaultMessage("Temporary: {0}")
    String tableTemporary(String isTemporary);

    @DefaultMessage("IoT type: {0}")
    String tableOfIoTType(String isOfIoTType);

    // column
    @DefaultMessage("Type: {0}")
    String columnType(String columnType);

    @DefaultMessage("Nullable: {0}")
    String columnNullable(String isNullable);

    @DefaultMessage("Virtual: {0}")
    String columnVirtual(String isVirtual);

    @DefaultMessage("hide non-changed objects")
    String hideNonChanged();
}
