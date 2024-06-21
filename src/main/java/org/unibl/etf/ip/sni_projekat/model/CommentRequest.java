package org.unibl.etf.ip.sni_projekat.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.unibl.etf.ip.sni_projekat.validation.sql.SQLValidation;
import org.unibl.etf.ip.sni_projekat.validation.xss.XSSValidation;

import java.sql.Timestamp;

@Data


public class CommentRequest {

    @NotBlank
    @XSSValidation
    @SQLValidation
    private String text;

    private Timestamp date;

    private Boolean approved;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer themeId;
}
