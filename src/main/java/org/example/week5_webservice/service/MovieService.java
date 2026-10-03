package org.example.week5_webservice.service;

import org.example.*;
import org.example.week5_webservice.dto.MovieRequest;
import org.example.week5_webservice.dto.MovieResponse;
import org.example.week5_webservice.repository.MovieRepository;
import org.example.week5_webservice.domain.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



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

    private MovieResponse toResponse(Movie request){


                return new MovieResponse(request.getId(),request.getTitle(),request.getdirector(),request.getrating(), request.getPubyear(),request.getCategory());
    }


    
    
    
}
