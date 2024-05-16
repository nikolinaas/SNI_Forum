package org.unibl.etf.ip.sni_projekat.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_has_permission", schema = "forum_sni", catalog = "")
public class UserHasPermissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Integer userId;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "permission_id")
    private Integer permissionId;


}
