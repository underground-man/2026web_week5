package org.example.week5_webservice.service;

import org.example.*;
import org.example.week5_webservice.dto.MovieRequest;
import org.example.week5_webservice.dto.MovieResponse;
import org.example.week5_webservice.repository.MovieRepository;
import org.example.week5_webservice.domain.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service

public class MovieService {
    private final MovieRepository repository;
    public MovieService(MovieRepository repository){
        this.repository = repository;
    }
    public MovieResponse create(MovieRequest request){

        return toResponse( repository.save(new Movie(null,request.title(),request.director(), request.rating(), request.pubyear(), request.category())));
    }

    public List<MovieResponse> findAll(){
        List<MovieResponse> mlist = new ArrayList<>();

        for (Movie b : repository.findAll()){
            mlist.add(toResponse(b));
        }
        return mlist;
    }

    public MovieResponse findbyId(long id){
        return toResponse(findBook(id));
    }


    public MovieResponse update(long id,MovieRequest m){
        Movie dummy = findBook(id);
        dummy.setTitle(m.title());
        dummy.setdirector(m.director());
        dummy.setCategory(m.category());
        dummy.setPubyear(m.pubyear());
        dummy.setrating(m.rating());
        return toResponse(repository.update(dummy));

    }

    public void delete(long id){
        findBook(id);
        repository.delete(id);
    }




    private Movie findBook(Long id){
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Can't find Movie"+id));
    }

    private MovieResponse toResponse(Movie request){


                return new MovieResponse(request.getId(),request.getTitle(),request.getdirector(),request.getrating(), request.getPubyear(),request.getCategory());
    }


    
    
    
}
