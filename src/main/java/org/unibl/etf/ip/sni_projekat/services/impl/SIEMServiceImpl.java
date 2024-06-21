package org.unibl.etf.ip.sni_projekat.services.impl;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.entities.SiemEntity;
import org.unibl.etf.ip.sni_projekat.repositories.SIEMEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.SIEMService;

import java.util.Date;

@Service
public class SIEMServiceImpl implements SIEMService {


private final SIEMEntityRepository siemEntityRepository;


    public SIEMServiceImpl(SIEMEntityRepository siemEntityRepository) {
        this.siemEntityRepository = siemEntityRepository;
    }


    @Override
    public void addLog(String log) {

        SiemEntity siemEntity = new SiemEntity();
        siemEntity.setId(null);
        siemEntity.setDate(new Date());
        siemEntity.setContent(log);
        siemEntityRepository.saveAndFlush(siemEntity);

    }
}
