package org.example.week5_webservice.repository;

import org.example.week5_webservice.domain.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Movie save(Movie b);
    List<Movie> findAll();
    Optional<Movie> findById(Long Id);
    Movie update(Movie b);
    void delete(long id);

}
