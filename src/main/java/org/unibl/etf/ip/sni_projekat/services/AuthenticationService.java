package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.model.LoginRequest;

public interface AuthenticationService {

    public JWTUser login(LoginRequest request);

}
