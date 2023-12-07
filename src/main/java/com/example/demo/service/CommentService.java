package com.example.demo.service;

import com.example.demo.models.Comment;
import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {

    Iterable<Comment> getAllComment();

    Comment getCommentById(long id);

    Comment save(Comment comment);

    void remove(Comment comment);

    Page<Comment>  getAllCommentByProduct(Product product, Pageable pageable);

    Long countAllByProduct(Product product);

}
