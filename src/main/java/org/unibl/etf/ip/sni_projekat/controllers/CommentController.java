package org.unibl.etf.ip.sni_projekat.controllers;

import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.sni_projekat.model.Comment;
import org.unibl.etf.ip.sni_projekat.model.CommentRequest;
import org.unibl.etf.ip.sni_projekat.model.entities.CommentEntity;
import org.unibl.etf.ip.sni_projekat.services.CommentService;
import org.unibl.etf.ip.sni_projekat.services.WAFService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600)
public class CommentController {

    private final CommentService commentService;
    private final WAFService wafService;

    public CommentController(CommentService commentService, WAFService wafService) {
        this.commentService = commentService;
        this.wafService = wafService;
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
    public ResponseEntity<Comment> approveComment(@PathVariable Integer id, @RequestBody @Valid CommentRequest comment, BindingResult bindingResult) {
        try {
            wafService.checkParamValidity(bindingResult);
            return new ResponseEntity<>(commentService.approveComment(id,comment),HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Comment> addComment(@RequestBody  @Valid CommentRequest req, BindingResult bindingResult)  {
        try {
            wafService.checkParamValidity(bindingResult);
            return new ResponseEntity<>(commentService.addComment(req),HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> editComment(@PathVariable Integer id, @RequestBody @Valid CommentRequest comment,BindingResult bindingResult) throws BadRequestException {

        try {
            wafService.checkParamValidity(bindingResult);
            return new ResponseEntity<>(commentService.editComment(id,comment),HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }
}
