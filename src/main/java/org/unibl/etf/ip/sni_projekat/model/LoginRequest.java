package org.unibl.etf.ip.sni_projekat.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.validation.sql.SQLValidation;
import org.unibl.etf.ip.sni_projekat.validation.xss.XSSValidation;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequest {

    @NotBlank
    @SQLValidation
    @XSSValidation
    private String username;
    @NotBlank
    @SQLValidation
    @XSSValidation
    private String password;

}
