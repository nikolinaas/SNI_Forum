package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.*;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;

public interface AuthenticationService {

    public UserEntity login(LoginRequest request);
    public void register(UserRequest request);

    User oauth2login(String token);

    public JWTUser verifyCode(Integer id, String code);

}
