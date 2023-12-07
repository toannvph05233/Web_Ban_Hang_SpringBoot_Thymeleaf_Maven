package com.example.demo.repository;

import com.example.demo.models.Comment;
import com.example.demo.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    Page<Comment> getAllByProduct(Product product, Pageable pageable);
    Long countAllByProduct (Product product);
}
