package com.otoil.dbcomparator.client.snapshots;

import java.util.UUID;

public class SnapshotsUniqueNameGeneratorUUIDImpl
        implements SnapshotsUniqueNameGenerator
{

    @Override
    public String generateUniqueName()
    {
        return UUID.randomUUID().toString();
    }

}
