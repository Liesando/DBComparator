package com.otoil.dbcomparator.client;

import com.google.gwt.user.client.ui.FileUpload;
import com.otoil.dbcomparator.client.interfaces.UniqueNameGenerator;


/**
 * Генератор уникальных имён для загруженных файлов
 * и {@link FileUpload}
 * @author kakeru
 *
 */
public class UniqueNameGeneratorMockImpl implements UniqueNameGenerator
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
