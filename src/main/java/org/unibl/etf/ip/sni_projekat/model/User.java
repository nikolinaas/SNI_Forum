package org.unibl.etf.ip.sni_projekat.model;

import jakarta.persistence.*;
import lombok.Data;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;
import org.unibl.etf.ip.sni_projekat.model.entities.PermissionEntity;

import java.util.List;

@Data
public class User {


    private Integer id;

    private String name;

    private String surname;

    private String username;

    private String password;

    private String email;

    private String code;

    private Role role;

    private Boolean activated;

    private List<CommentEntity> commentsById;

 //   private List<PermissionEntity> permissions;

}
