package com.otoil.dbcomparator.server;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Сервлет для загрузки файлов на сервер
 * 
 * @author kakeru
 */
public class FileUploadServlet extends HttpServlet
{

    private static final int FILE_MAX_SIZE = 5000000;

    // TODO: send exceptions info to client
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // always save on disk
        factory.setSizeThreshold(0);

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(FILE_MAX_SIZE);

        try
        {
            Iterator<FileItem> it = upload.parseRequest(req).iterator();
            while (it.hasNext())
            {
                FileItem item = it.next();

                if (!item.isFormField())
                {
                    File f = new File(System.getProperty("java.io.tmpdir"),
                        item.getFieldName() + ".zip");
                    item.write(f);
                }
            }
        }
        catch (FileUploadException e)
        {
            // TODO: display error to the client
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            // TODO: display error to the client
        }
        catch (IOException e)
        {
            // TODO: display error to the client
        }
        catch (SecurityException e)
        {
            // TODO: display error to the client
        }
        catch (Exception e)
        {
            // if an error occurs in item.write() method
            // TODO: display error to the client
        }

        // if reached then we have files successfully uploaded
    }
}
