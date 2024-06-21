package org.unibl.etf.ip.sni_projekat.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.validation.sql.SQLValidation;
import org.unibl.etf.ip.sni_projekat.validation.xss.XSSValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TokenRequest {

    @NotBlank
    @NotNull
    @SQLValidation
    @XSSValidation
    private String tokenId;

}
