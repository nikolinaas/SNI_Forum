package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.LoginRequest;

public interface AuthenticationService {

    public Long login(LoginRequest request);

}
