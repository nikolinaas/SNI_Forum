package org.unibl.etf.ip.sni_projekat.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException()
    {
        super("Entity not found");
    }

    public NotFoundException(String message)
    {
        super(message);
    }
}
