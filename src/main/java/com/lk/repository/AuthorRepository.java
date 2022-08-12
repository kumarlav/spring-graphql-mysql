package com.lk.repository;

import com.lk.models.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Iterable<Author> findALLByBookId(Integer bookId);
}