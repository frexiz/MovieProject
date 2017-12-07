package com.catalog.movies.core.comments;

public interface CommentService {
    Comment saveAndFlush(Comment comment);

    String validate(AddCommentDTO addCommentDTO);

    void delete(Comment comment);
    Comment findOne(Integer id);

}
