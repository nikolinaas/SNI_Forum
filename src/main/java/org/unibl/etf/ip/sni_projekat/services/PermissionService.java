package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.entities.PermissionEntity;

import java.util.List;

public interface PermissionService {
    List<PermissionEntity> getAll();
}
