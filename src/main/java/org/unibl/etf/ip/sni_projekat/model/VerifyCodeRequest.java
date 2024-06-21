package org.unibl.etf.ip.sni_projekat.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.validation.sql.SQLValidation;
import org.unibl.etf.ip.sni_projekat.validation.xss.XSSValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLValidation
@XSSValidation
public class VerifyCodeRequest {

    private String userCode;
    private String codeToVerify;
}
