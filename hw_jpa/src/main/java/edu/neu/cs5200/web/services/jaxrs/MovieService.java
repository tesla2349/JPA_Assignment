package edu.neu.cs5200.web.services.jaxrs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.cs5200.orm.jpa.daos.ActorDao;
import edu.neu.cs5200.orm.jpa.daos.MovieDao;
import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Movie;


@RestController
public class MovieService {
	
	
	ActorDao actorDao;
	
	
	MovieDao movieDao;

	@GetMapping("/actor")
	public List<Actor> findAllActors() {
		return actorDao.findAllActors();
	}
	
	@GetMapping("/actor/{aid}")
	public Actor findActorById(@PathVariable("ID") int aid) {
		return actorDao.findActorById(aid);
	}
	
	@GetMapping("/actor/{aid}/movie")
	public List<Movie> findMoviesByActorId(@PathVariable("ID") int aid) {
		Actor actor =  actorDao.findActorById(aid);
		return actor.getMovies();
	}
	
	
	@GetMapping("/movie")
	public List<Movie> findAllMovies() {
		return movieDao.findAllMovies();
	}

	@GetMapping("/movie/{mid}")
	public Movie findMovieById(@PathVariable("ID") int mid) {
		return movieDao.findMovieById(mid);
	}
	
	@GetMapping("/movie/{mid}/actor")
	public List<Actor> findActorsByMovieId(@PathVariable("ID") int mid) {
		Movie movie =  movieDao.findMovieById(mid);
		return movie.getActors();
	}
}



