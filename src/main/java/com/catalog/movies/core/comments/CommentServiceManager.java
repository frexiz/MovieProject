package com.catalog.movies.core.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceManager implements CommentService{
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Comment saveAndFlush(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public String validate(AddCommentDTO addCommentDTO) {
        String error = null;
        if (addCommentDTO.getContent().contains("javascript")){
            error = "js";
        }
        return error;
    }

    @Override
    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    @Override
    public Comment findOne(Integer id) {
       return this.commentRepository.findOne(id);

    }
}
