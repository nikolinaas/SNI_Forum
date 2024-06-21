package org.unibl.etf.ip.sni_projekat.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.validation.sql.SQLValidation;
import org.unibl.etf.ip.sni_projekat.validation.xss.XSSValidation;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CodeRequest {

    @NotBlank
    @SQLValidation
    @XSSValidation
    String code;

}
