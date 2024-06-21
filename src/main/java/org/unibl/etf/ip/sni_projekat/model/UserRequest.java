package org.unibl.etf.ip.sni_projekat.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.ip.sni_projekat.model.entities.PermissionEntity;
import org.unibl.etf.ip.sni_projekat.validation.sql.SQLValidation;
import org.unibl.etf.ip.sni_projekat.validation.xss.XSSValidation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRequest {

    @NotBlank
    @SQLValidation
    @XSSValidation
    private String name;
    @NotBlank
    @SQLValidation
    @XSSValidation
    private String surname;
    @NotBlank
    @SQLValidation
    @XSSValidation
    private String username;
    @NotBlank
    @SQLValidation
    @XSSValidation
    private String password;
    @NotBlank
    @SQLValidation
    @XSSValidation
    private String email;
    private String code;
    private Role role;
    private Boolean activated;
    private List<PermissionEntity> permissions;

}
