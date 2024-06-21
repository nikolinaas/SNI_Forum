package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.LoginResponse;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.UserRequest;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;

import java.util.List;

public interface UserService {

    public LoginResponse findById(Integer id);

    UserEntity getById(Integer id);

    public LoginResponse findByUsername(String username);

    public List<User> getUnacativeUsers();
    public List<User> getActiveUsers();

    User getUserById(Integer id);

    User activateUser(Integer id, UserRequest user);

    User deactivateUser(Integer id,UserRequest user);
    User changePermissions(Integer id,UserRequest user);
}
