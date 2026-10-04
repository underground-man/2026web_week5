package org.example.week5_webservice.controller;

import org.example.week5_webservice.dto.MovieRequest;
import org.example.week5_webservice.dto.MovieResponse;
import org.example.week5_webservice.service.MovieService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import org.example.week5_webservice.*;
import org.example.week5_webservice.dto.MovieResponse;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

        public MovieController(MovieService movieService){
            this.movieService =movieService;
        }

        @PostMapping
        public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request){
            return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(request));
        }


        @GetMapping
        public List<MovieResponse> findAll(){
            return movieService.findAll();
        }

        @GetMapping("/{id}")
        public MovieResponse findbyid(@PathVariable Long id){
            return movieService.findbyId(id);
        }

        @PutMapping("/{id}")
        public MovieResponse update(@PathVariable Long id, @RequestBody MovieRequest m){
            return movieService.update(id,m);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id){
            movieService.delete(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping
        public List<MovieResponse> findCat(@RequestParam String category){
            return movieService.findCat(category);

        }
}
