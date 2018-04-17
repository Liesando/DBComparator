package com.otoil.dbcomparator.client.snapshots;


/**
 * Фейковый генератор уникальных имён
 * 
 * @author Sergey Medelyan
 */
public class SnapshotsUniqueNameGeneratorMockImpl
        implements SnapshotsUniqueNameGenerator
{
    private static int last = 100;

    @Override
    public String generateUniqueName()
    {
        // TODO Auto-generated method stub
        last++;
        return Integer.toString(last);
    }

}
