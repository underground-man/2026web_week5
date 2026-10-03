package org.example.week5_webservice.repository;


public interface BookRepository {
    Book save(Book b);
    List<Book> findAll();
    Optional<Book> findById(Long Id);
    Book update(Book b);
    Void delete(long id);

}
