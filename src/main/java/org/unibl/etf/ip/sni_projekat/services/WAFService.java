package org.unibl.etf.ip.sni_projekat.services;

import org.apache.coyote.BadRequestException;
import org.springframework.validation.BindingResult;

public interface WAFService {

    void checkParamValidity(BindingResult bindingResult) throws BadRequestException;

}
