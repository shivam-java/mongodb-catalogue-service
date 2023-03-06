package com.mongodb_catalogue_service.error;

public class DataNotAvaialableException  extends RuntimeException
{
    private static final long serialVersionUID=1l;
    public DataNotAvaialableException(String msg)
    {
        super(msg);
    }
}
