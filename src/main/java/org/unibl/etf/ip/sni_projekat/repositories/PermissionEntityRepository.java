package org.unibl.etf.ip.sni_projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.sni_projekat.model.entities.PermissionEntity;

public interface PermissionEntityRepository extends JpaRepository<PermissionEntity,Integer> {
}
