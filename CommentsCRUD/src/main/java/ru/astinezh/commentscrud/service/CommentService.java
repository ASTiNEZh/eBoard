package ru.astinezh.commentscrud.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ASTiNEZh.dto.CommentDTO;
import ru.astinezh.commentscrud.entity.Comment;
import ru.astinezh.commentscrud.reposytory.CommentRepository;

import java.util.Objects;
import java.util.UUID;

@Service
public class CommentService {
    private final ModelMapper modelMapper = new ModelMapper();
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDTO findById(UUID uuid) {
        Comment comment = commentRepository.findById(uuid).orElse(null);
        if (Objects.nonNull(comment) && !comment.isDeleted())
            return modelMapper.map(comment, CommentDTO.class);
        else
            return null;
    }

    public void create(CommentDTO commentDTO) {
        if (!commentRepository.existsById(commentDTO.getUuid()))
            commentRepository.save(modelMapper.map(commentDTO, Comment.class));
    }

    public void update(UUID uuid, CommentDTO commentDTO) {
        if (commentRepository.findById(uuid).isPresent())
            commentRepository.save(modelMapper.map(commentDTO, Comment.class));
    }

    public void delete(UUID uuid) {
        Comment comment = commentRepository.findById(uuid).orElse(null);
        if (comment != null) {
            comment.setDeleted(true);
            commentRepository.save(comment);
        }
    }
}
