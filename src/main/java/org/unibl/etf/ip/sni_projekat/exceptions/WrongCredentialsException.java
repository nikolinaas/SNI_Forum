package org.unibl.etf.ip.sni_projekat.exceptions;

public class WrongCredentialsException extends RuntimeException{

    public WrongCredentialsException()
    {
        super("Wrong credentials!");
    }

    public WrongCredentialsException(String message)
    {
        super(message);
    }
}
