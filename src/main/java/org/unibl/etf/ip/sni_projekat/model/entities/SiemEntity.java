package org.unibl.etf.ip.sni_projekat.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "siem", schema = "forum_sni", catalog = "")
public class SiemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "content")
    private String content;
    @Basic
    @Column(name = "date")
    private Date date;

}
