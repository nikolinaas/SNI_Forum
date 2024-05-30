package org.unibl.etf.ip.sni_projekat.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.model.Role;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "forum_sni", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;


    @Basic
    @Column(name = "name")
    private String name;


    @Basic
    @Column(name = "surname")
    private String surname;


    @Basic
    @Column(name = "username")
    private String username;


    @Basic
    @Column(name = "password")
    private String password;


    @Basic
    @Column(name = "email")
    private String email;


    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Basic
    @Column(name = "activated")
    private Boolean activated;


    @OneToMany(mappedBy = "userByUserId")
    private List<CommentEntity> commentsById;

   /* @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_has_permission",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<PermissionEntity> permissions;*/
}
