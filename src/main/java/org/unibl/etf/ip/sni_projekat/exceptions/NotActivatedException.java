package org.unibl.etf.ip.sni_projekat.exceptions;

public class NotActivatedException extends RuntimeException{

    public NotActivatedException()
    {
        super("Account is not activated");
    }

    public NotActivatedException(String message)
    {
        super(message);
    }

}
