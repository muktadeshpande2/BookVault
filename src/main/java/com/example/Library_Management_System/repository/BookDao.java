package com.example.Library_Management_System.repository;

import com.example.Library_Management_System.model.Book;
import com.example.Library_Management_System.model.Genre;
import com.example.Library_Management_System.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDao extends JpaRepository<Book, Integer> {

    List<Book> findByBookName(String name);
    List<Book> findByGenre(Genre genre);
    List<Book> findByPublication(Publication publication);

    @Query("Select b from Book b, Author a where b.book_author.id = a.id and a.name = ?1")
    List<Book> findByAuthorName(String authorName);


}
