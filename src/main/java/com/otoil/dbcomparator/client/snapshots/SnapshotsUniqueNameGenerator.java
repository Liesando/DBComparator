package com.otoil.dbcomparator.client.snapshots;


import com.google.gwt.user.client.ui.FileUpload;


/**
 * Генератор уникальных имён для загружаемых файлов и {@link FileUpload}
 * 
 * @author Sergey Medelyan
 */
public interface SnapshotsUniqueNameGenerator
{
    String generateUniqueName();
}
