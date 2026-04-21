package ru.astinezh.commentscrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.ASTiNEZh.controller.CommentsApi;
import ru.ASTiNEZh.dto.CommentDTO;
import ru.astinezh.commentscrud.service.CommentService;

import java.util.UUID;

@RestController
public class CommentController implements CommentsApi {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<Void> createComment(CommentDTO commentDTO) {
        commentService.create(commentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PreAuthorize("hasRole('role_admin')")
    public ResponseEntity<CommentDTO> deleteComment(UUID uuid) {
        commentService.delete(uuid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommentDTO> getComment(UUID uuid) {
        return ResponseEntity.ok(commentService.findById(uuid));
    }

    @Override
    @PreAuthorize("hasRole('role_user')")
    public ResponseEntity<CommentDTO> updateComment(UUID uuid, CommentDTO commentDTO) {
        commentService.update(uuid, commentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
