package org.unibl.etf.ip.sni_projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {



    Optional<UserEntity> findByUsername(String username);
    UserEntity getById(Integer id);


    List<UserEntity> getAllByActivated(Boolean activated);

    UserEntity findByEmail(String email);

    Boolean existsByEmail(String email);
}
