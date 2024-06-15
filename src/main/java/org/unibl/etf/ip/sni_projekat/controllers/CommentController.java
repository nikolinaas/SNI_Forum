package org.unibl.etf.ip.sni_projekat.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.Comment;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;
import org.unibl.etf.ip.sni_projekat.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Integer id){
        return commentService.getCommentById(id);
    }

    @GetMapping("/notApproved")
    public List<Comment> getNotApprovedComments(){
        return this.commentService.getAllNotApprovedComments();
    }

    @GetMapping("/approved/{id}")
    public List<Comment> getApprovedCommentsByThemeId(@PathVariable Integer id){
        return this.commentService.getApprovedCommentsByTheme(id);
    }

    @PutMapping("/approveComment/{id}")
    public Comment approveComment(@PathVariable Integer id, @RequestBody Comment comment){
        return this.commentService.approveComment(id,comment);
    }
}
