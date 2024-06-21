package org.unibl.etf.ip.sni_projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.sni_projekat.model.entities.BlacklistEntity;

public interface BlackListEntityRepository extends JpaRepository<BlacklistEntity,Integer> {

    public Boolean existsByToken(String token);
}
