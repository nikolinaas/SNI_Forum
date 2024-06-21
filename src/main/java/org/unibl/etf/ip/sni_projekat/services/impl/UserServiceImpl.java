package org.unibl.etf.ip.sni_projekat.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.exceptions.NotFoundException;
import org.unibl.etf.ip.sni_projekat.model.LoginResponse;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.UserRequest;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.repositories.UserEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.EmailService;
import org.unibl.etf.ip.sni_projekat.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserEntityRepository repository;
    private  final ModelMapper mapper;
    private final EmailService emailService;
    @PersistenceContext
    private EntityManager entityManager;

    public UserServiceImpl(UserEntityRepository repository, ModelMapper mapper, EmailService emailService) {
        this.repository = repository;
        this.mapper = mapper;
        this.emailService = emailService;
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
    public List<User> getActiveUsers() {
        return repository.getAllByActivated(true).stream().map(l ->mapper.map(l,User.class)).collect(Collectors.toList());
    }

    @Override
    public User getUserById(Integer id){
     return    mapper.map(repository.getById(id),User.class);
    }

    @Override
    public User activateUser(Integer id, UserRequest user){


        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

       UserEntity entity = mapper.map(user, UserEntity.class);
        entity.setId(id);
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);

        //poslati mejl da je aktivirano
        try {

            emailService.sendRegistrationEmail(entity.getEmail(),entity.getName());
        }catch (Exception e){

        }
        return mapper.map(entity, User.class);

    }

    @Override
    public User deactivateUser(Integer id, UserRequest user) {


        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        UserEntity entity = mapper.map(user, UserEntity.class);
        entity.setId(id);
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);


        return mapper.map(entity, User.class);
    }

    @Override
    public User changePermissions(Integer id, UserRequest user) {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        UserEntity entity = mapper.map(user, UserEntity.class);
        entity.setId(id);
        entity = repository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return mapper.map(entity, User.class);
    }
}

