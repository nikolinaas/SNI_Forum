package org.unibl.etf.ip.sni_projekat.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CommentRequest {

    private String text;

    private Timestamp date;

    private Boolean approved;

    private Integer userId;

    private Integer themeId;
}
