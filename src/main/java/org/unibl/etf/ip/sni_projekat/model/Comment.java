package org.unibl.etf.ip.sni_projekat.model;

import jakarta.persistence.*;
import lombok.Data;
import org.unibl.etf.ip.sni_projekat.model.entities.ThemeEntity;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;


import java.sql.Timestamp;

@Data
public class Comment {

    private Integer id;

    private String text;

    private Timestamp date;

    private Boolean approved;

    private Integer userId;

    private Integer themeId;

private String userByUserIdName;
    private String userByUserIdSurname;

    private String themeByThemeIdThemeName;
}
