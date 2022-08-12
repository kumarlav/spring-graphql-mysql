package com.lk.resolver;

import com.lk.enums.ListOrder;
import com.lk.models.Author;
import com.lk.models.Book;
import com.lk.repository.AuthorRepository;
import com.lk.repository.BookRepository;
import com.lk.types.BookFilter;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Comparator;
import java.util.List;


@Controller
@AllArgsConstructor
public class BookQuery{

    BookRepository bookRepository;
    AuthorRepository authorRepository;

    @QueryMapping
    public Iterable<Book> allBook(){
        return bookRepository.findAll();
    }

    @QueryMapping
    public Book getBookByName(@Argument("filter") BookFilter bookFilter){
        return bookRepository.findBookByName(bookFilter.getName());
    }

    @QueryMapping
    public Iterable<Author> allAuthor(){
        return authorRepository.findAll();
    }

    @SchemaMapping(typeName="Book", field="author")
    public Iterable<Author> getAuthor(Book book, @Argument ListOrder order) {
        List<Author> allAuthors = (List<Author>) authorRepository.findALLByBookId(book.getId());
        if (order != null) {
            switch (order) {
                case ASC:
                    allAuthors.sort(Comparator.comparingInt(Author::getStarRating));
                case DESC:
                    allAuthors.sort(Comparator.comparingInt(Author::getStarRating).reversed());
            }
        }
        return allAuthors;
    }

}