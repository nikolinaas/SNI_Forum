package org.unibl.etf.ip.sni_projekat.services.impl;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.entities.BlacklistEntity;
import org.unibl.etf.ip.sni_projekat.repositories.BlackListEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.BlackListService;

@Service
public class BlackListServiceImpl implements BlackListService {

private final BlackListEntityRepository blackListEntityRepository;

    public BlackListServiceImpl(BlackListEntityRepository blackListEntityRepository) {
        this.blackListEntityRepository = blackListEntityRepository;
    }

    @Override
    public void addJWTtoBlackList(String token) {
        BlacklistEntity blacklistEntity = new BlacklistEntity();
        blacklistEntity.setId(null);
        blacklistEntity.setToken(token);
        blackListEntityRepository.saveAndFlush(blacklistEntity);
        System.out.println("Token added to blacklist:" + token);
    }

    @Override
    public Boolean isTokenValid(String token){

        return !blackListEntityRepository.existsByToken(token);
    }
}
