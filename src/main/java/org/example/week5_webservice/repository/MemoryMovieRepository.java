package org.example.week5_webservice.repository;

import org.example.week5_webservice.domain.Movie;

import java.util.*;


public class MemoryMovieRepository implements MovieRepository {
    private final Map<Long, Movie> store = new LinkedHashMap<>();
    private long spekey =0L;



    @Override
    public Movie save(Movie b) {
        b.setId(++spekey);
        store.put(b.getId(),b);
        return b;
    }

    @Override
    public List<Movie> findAll() {

        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Movie> findById(Long Id) {

        return Optional.ofNullable(store.get(Id));
    }

    @Override
    public Movie update(Movie b) {
        store.put(b.getId(), b);
        return b;
    }

    @Override
    public void delete(long id) {
        store.remove(id);

    }
}
