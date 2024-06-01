package org.unibl.etf.ip.sni_projekat.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.Theme;
import org.unibl.etf.ip.sni_projekat.repositories.ThemeEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.ThemeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeServiceImpl implements ThemeService {


    private final ThemeEntityRepository repo;


    private final ModelMapper mapper;

    public ThemeServiceImpl(ThemeEntityRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<Theme> getAll() {
        return repo.findAll().stream().map( l -> mapper.map(l,Theme.class)).collect(Collectors.toList());
    }
}
