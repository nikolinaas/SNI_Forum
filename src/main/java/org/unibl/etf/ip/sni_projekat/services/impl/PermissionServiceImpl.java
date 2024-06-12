package org.unibl.etf.ip.sni_projekat.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.entities.PermissionEntity;
import org.unibl.etf.ip.sni_projekat.repositories.PermissionEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {


    private final ModelMapper mapper;

    private final PermissionEntityRepository repository;
    public PermissionServiceImpl(ModelMapper mapper, PermissionEntityRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<PermissionEntity> getAll(){

        return repository.findAll();
    }
}
