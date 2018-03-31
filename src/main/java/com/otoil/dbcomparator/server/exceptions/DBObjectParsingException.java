package com.otoil.dbcomparator.server.exceptions;


public class DBObjectParsingException extends Exception
{
    public DBObjectParsingException()
    {
    }

    public DBObjectParsingException(String message)
    {
        super(message);
    }

    public DBObjectParsingException(Throwable cause)
    {
        super(cause);
    }
}
