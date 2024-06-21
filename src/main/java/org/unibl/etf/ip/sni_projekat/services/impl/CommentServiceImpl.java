package org.unibl.etf.ip.sni_projekat.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.exceptions.NotFoundException;
import org.unibl.etf.ip.sni_projekat.model.Comment;
import org.unibl.etf.ip.sni_projekat.model.CommentRequest;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.repositories.CommentEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CommentServiceImpl implements CommentService {


    private final ModelMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;
    private final CommentEntityRepository commentEntityRepository;


    public CommentServiceImpl(ModelMapper mapper, CommentEntityRepository commentEntityRepository) {
        this.mapper = mapper;
        this.commentEntityRepository = commentEntityRepository;
    }


    @Override
    public List<Comment> getAllNotApprovedComments(){
        return this.commentEntityRepository.getAllByApproved(false).stream().map(el -> mapper.map(el,Comment.class)).collect(Collectors.toList());


    }

    @Override
    public Comment approveComment(Integer id,CommentRequest com) {
        if (!commentEntityRepository.existsById(id)) {
            throw new NotFoundException();
        }

        CommentEntity entity = mapper.map(com, CommentEntity.class);
        entity.setId(id);
        entity = commentEntityRepository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return mapper.map(entity, Comment.class);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return mapper.map(commentEntityRepository.getById(id),Comment.class);
    }

    @Override
    public List<Comment> getApprovedCommentsByTheme(Integer id) {
        return commentEntityRepository.findTop20ByApprovedAndThemeIdOrderByDateDesc(true,id).stream().map(el -> mapper.map(el,Comment.class)).collect(Collectors.toList());
    }

    @Override
    public Comment addComment(CommentRequest request) {
        CommentEntity entity = mapper.map(request, CommentEntity.class);
        entity.setId(null);
        entity = commentEntityRepository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return mapper.map(entity, Comment.class);
    }

    @Override
    public Comment editComment(Integer id, CommentRequest com) {
        if (!commentEntityRepository.existsById(id)) {
            throw new NotFoundException();
        }

        CommentEntity entity = mapper.map(com, CommentEntity.class);
        entity.setId(id);
        entity = commentEntityRepository.saveAndFlush(entity);
        entityManager.refresh(entity);
        return mapper.map(entity, Comment.class);
    }

}
