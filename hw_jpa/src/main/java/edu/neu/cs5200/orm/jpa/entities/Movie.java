package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity

public class Movie implements Serializable {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	@ManyToOne
	private MovieLibrary library;
	
	@ManyToMany
	@JoinTable(name="MOVIE2ACTOR")
	private List<Actor> actors = null;
	
	@ManyToMany
	@JoinTable(name="MOVIE2DIRECTOR")
	private List<Director> directors = null;
	
	private static final long serialVersionUID = 1L;

	public List<Director> getDirectors() {
		if(directors == null) {
			directors = new ArrayList<>();
		}
		return directors;
	}

	public void setDirectors(List<Director> directors) {
		this.directors = directors;
		for(Director director: directors) {
			director.getMovies().add(this);
		}
	}
	
	public List<Actor> getActors() {
		if(actors == null) {
			actors = new ArrayList<>();
		}
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
		for(Actor actor: actors) {
			actor.getMovies().add(this);
		}
	}


	public Movie() {
		super();
	}  

	public Movie(String title) {
		super();
		this.title = title;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MovieLibrary getLibrary() {
		return library;
	}

	public void setLibrary(MovieLibrary library) {
		this.library = library;
	}

}
//@Entity
//public class Movie implements Serializable{
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private int id;
//	private String title;
//	@ManyToOne()
//	@JsonIgnore
//	private MovieLibrary library;
//
//	@ManyToMany
//	@JoinTable(name="Movie2Actor",
//	joinColumns=@JoinColumn(name="MOVIE_ID", 
//	referencedColumnName="ID"),
//	inverseJoinColumns=@JoinColumn(name=
//	"ACTOR_ID", referencedColumnName="ID"))
//	@JsonIgnore
//	private List<Actor> actors;
//
//	@ManyToMany
//	@JoinTable(name="Movie2Director",
//	joinColumns=@JoinColumn(name="MOVIE_ID", 
//	referencedColumnName="ID"),
//	inverseJoinColumns=@JoinColumn(name=
//	"DIRECTOR_ID", referencedColumnName="ID"))
//	@JsonIgnore
//	private List<Director> directors;
//
//	private static final long serialVersionUID = 1L;
//	
//	public Movie() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	
//	public Movie(String title) {
//		super();
//		this.title = title;
//	}
//
//	public int getId() {
//		return id;
//	}
//	public void setId(int id) {
//		this.id = id;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public MovieLibrary getLibrary() {
//		return library;
//	}
//	public void setLibrary(MovieLibrary library) {
//		this.library = library;
//		if(!library.getMovies().contains(this)) {
//			library.getMovies().add(this);
//		}
//	}
//
//
//}
