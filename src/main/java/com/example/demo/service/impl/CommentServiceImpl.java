package com.example.demo.service.impl;

import com.example.demo.models.Comment;
import com.example.demo.models.Product;
import com.example.demo.repository.CommentRepository;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Iterable<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void remove(Comment comment) {
        if (comment != null)
            commentRepository.delete(comment);
    }

    @Override
    public Page<Comment> getAllCommentByProduct(Product product, Pageable pageable) {
        return commentRepository.getAllByProduct(product,pageable);
    }

    @Override
    public Long countAllByProduct(Product product) {
        return commentRepository.countAllByProduct(product);
    }
}
