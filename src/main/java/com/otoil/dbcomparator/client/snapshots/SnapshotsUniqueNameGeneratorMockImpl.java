package com.otoil.dbcomparator.client.snapshots;

import com.google.gwt.user.client.ui.FileUpload;


/**
 * Генератор уникальных имён для загруженных файлов
 * и {@link FileUpload}
 * @author kakeru
 *
 */
public class SnapshotsUniqueNameGeneratorMockImpl implements SnapshotsUniqueNameGenerator
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
