package org.unibl.etf.ip.sni_projekat.services.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.unibl.etf.ip.sni_projekat.services.BlackListService;
import org.unibl.etf.ip.sni_projekat.services.SIEMService;
import org.unibl.etf.ip.sni_projekat.services.WAFService;

import java.util.List;

@Service
public class WAFServiceImpl implements WAFService {

    private final HttpServletRequest request;
    private final SIEMService siemService;

    private final BlackListService blackListService;
    public WAFServiceImpl(HttpServletRequest request, SIEMService siemService, BlackListService blackListService) {
        this.request = request;
        this.siemService = siemService;
        this.blackListService = blackListService;
    }

    @Override
    public void checkParamValidity(BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()){
            System.out.println("checkParamValidity");
            StringBuilder errorBuilder = new StringBuilder("Address: " + request.getRemoteAddr() + ".Validation failed: ");

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorBuilder.append(fieldError.getDefaultMessage()).append(", ");
            }
            errorBuilder.setLength(errorBuilder.length() - 2);
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);
                System.out.println(errorBuilder.toString());
                blackListService.addJWTtoBlackList(jwt);

            }
            siemService.addLog(errorBuilder.toString());
            throw new BadRequestException();

        }
    }
}
