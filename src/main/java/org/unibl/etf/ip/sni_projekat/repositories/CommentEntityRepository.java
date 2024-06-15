package org.unibl.etf.ip.sni_projekat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;

import java.util.List;

public interface CommentEntityRepository extends JpaRepository<CommentEntity,Integer> {

    public List<CommentEntity> getAllByApproved(Boolean approved);
    public CommentEntity getById(Integer id);
    public List<CommentEntity> getByApprovedAndAndThemeId(Boolean approved,Integer id);

    public List<CommentEntity> findTop20ByApprovedAndThemeIdOrderByDateDesc(Boolean approved,Integer id);
}
