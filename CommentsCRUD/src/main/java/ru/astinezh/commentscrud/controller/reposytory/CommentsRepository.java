package ru.astinezh.commentscrud.controller.reposytory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.astinezh.commentscrud.controller.entity.Comment;

import java.util.UUID;

public interface CommentsRepository extends JpaRepository<Comment, UUID> {
}
