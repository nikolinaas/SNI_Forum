package org.unibl.etf.ip.sni_projekat.services;

import org.unibl.etf.ip.sni_projekat.model.Comment;
import org.unibl.etf.ip.sni_projekat.model.CommentRequest;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;

import java.util.List;

public interface CommentService {
    List<Comment> getAllNotApprovedComments();

    Comment approveComment(Integer id, CommentRequest com);

    Comment getCommentById(Integer id);

    List<Comment> getApprovedCommentsByTheme(Integer id);

    Comment addComment(CommentRequest request);

    Comment editComment(Integer id, CommentRequest comment);
}
