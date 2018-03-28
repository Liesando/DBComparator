package com.otoil.dbcomparator.client.interfaces;

import com.google.gwt.user.client.ui.FileUpload;

/**
 * Генератор уникальных имён для загружаемых файлов
 * и {@link FileUpload}
 * @author kakeru
 *
 */
public interface UniqueNameGenerator
{
    String generateUniqueName();
}
