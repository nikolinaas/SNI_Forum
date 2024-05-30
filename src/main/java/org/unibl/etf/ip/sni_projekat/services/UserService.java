package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.LoginResponse;
import org.unibl.etf.ip.sni_projekat.model.User;

public interface UserService {

    public LoginResponse findById(Integer id);
    public LoginResponse findByUsername(String username);

}
