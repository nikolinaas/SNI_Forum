package org.unibl.etf.ip.sni_projekat.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.LoginResponse;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.repositories.UserEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository repository;
    private  final ModelMapper mapper;

    public UserServiceImpl(UserEntityRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public LoginResponse findById(Integer id) {
        return mapper.map(repository.findById(id),LoginResponse.class);
    }

    @Override
    public UserEntity getById(Integer id){
        return repository.getById(id);
    }
    @Override
    public LoginResponse findByUsername(String username) {
        return mapper.map(repository.findByUsername(username),LoginResponse.class);
    }

    @Override
    public List<User> getUnacativeUsers() {
        return repository.getAllByActivated(false).stream().map(l ->mapper.map(l,User.class)).collect(Collectors.toList());
    }

    @Override
    public User getUserById(Integer id){
     return    mapper.map(repository.getById(id),User.class);
    }

    @Override
    public User activateUser(Integer id){
        return null;
    }
}
