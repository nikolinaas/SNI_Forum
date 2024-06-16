package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.User;

public interface OAuth2Service {

    User oauth2login(String token);

}
