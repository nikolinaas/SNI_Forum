package org.unibl.etf.ip.sni_projekat.services;

public interface BlackListService {

    void addJWTtoBlackList(String token);

    Boolean isTokenValid(String token);
}
