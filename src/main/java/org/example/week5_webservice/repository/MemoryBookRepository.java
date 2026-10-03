package org.example.week5_webservice.repository;

import org.example.week5_webservice.domain.Movie;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;





public class MemoryMovieRepository implements MovieRepository {
    private final Map<Long, Movie> store = new LinkedHashMap<>();
    private long spekey =0L;



    @Override
    public Movie save(Movie b) {
        b.setId(++spekey);

        return null;
    }

    @Override
    public List<Movie> findAll() {
        return null;
    }

    @Override
    public Optional<Movie> findById(Long Id) {
        return null;
    }

    @Override
    public Movie update(Movie b) {
        return null;
    }

    @Override
    public Void delete(long id) {
        return null;
    }
}
