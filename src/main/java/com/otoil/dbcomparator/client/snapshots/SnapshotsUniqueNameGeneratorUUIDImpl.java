package com.otoil.dbcomparator.client.snapshots;


import java.util.UUID;


/**
 * Фактически используемый генератор уникальных имён
 * 
 * @author Sergey Medelyan
 */
public class SnapshotsUniqueNameGeneratorUUIDImpl
        implements SnapshotsUniqueNameGenerator
{

    @Override
    public String generateUniqueName()
    {
        return UUID.randomUUID().toString();
    }

}
