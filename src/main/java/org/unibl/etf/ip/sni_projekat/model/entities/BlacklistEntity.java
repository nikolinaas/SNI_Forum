package org.unibl.etf.ip.sni_projekat.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "blacklist", schema = "forum_sni", catalog = "")
public class BlacklistEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "token")
    private String token;


}
