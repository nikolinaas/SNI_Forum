package org.unibl.etf.ip.sni_projekat.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyCodeRequest {

    private String userCode;
    private String codeToVerify;
}
