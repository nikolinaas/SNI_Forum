package org.unibl.etf.ip.sni_projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;

public interface CommentEntityRepository extends JpaRepository<CommentEntity,Integer> {
}
