package org.unibl.etf.ip.sni_projekat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    private String name;

    private String surname;

    private String username;

    private String password;

    private String email;

    private String code;

    private Role role;

    private Boolean activated;

}
