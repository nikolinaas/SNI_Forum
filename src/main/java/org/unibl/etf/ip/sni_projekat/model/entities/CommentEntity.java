package org.unibl.etf.ip.sni_projekat.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment", schema = "forum_sni", catalog = "")
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "text")
    private String text;
    @Basic
    @Column(name = "date")
    private Timestamp date;
    @Basic
    @Column(name = "approved")
    private Boolean approved;
    @Basic
    @Column(name = "user_id")
    private Integer userId;
    @Basic
    @Column(name = "theme_id")
    private Integer themeId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id",  nullable = false, insertable=false, updatable=false)
    private UserEntity userByUserId;

    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id"  ,nullable = false, insertable=false, updatable=false)

    private ThemeEntity themeByThemeId;


}
