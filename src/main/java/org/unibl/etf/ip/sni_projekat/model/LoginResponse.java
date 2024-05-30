package org.unibl.etf.ip.sni_projekat.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends UserEntity {

    private String token;

}
