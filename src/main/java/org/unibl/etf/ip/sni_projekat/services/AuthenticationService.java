package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.JWTUser;
import org.unibl.etf.ip.sni_projekat.model.LoginRequest;
import org.unibl.etf.ip.sni_projekat.model.UserRequest;
import org.unibl.etf.ip.sni_projekat.model.VerifyCodeRequest;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;

public interface AuthenticationService {

    public UserEntity login(LoginRequest request);
    public void register(UserRequest request);

    public JWTUser verifyCode(Integer id, String code);

}
