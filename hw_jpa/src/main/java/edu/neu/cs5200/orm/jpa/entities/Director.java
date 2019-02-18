package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity

public class Director extends Person implements Serializable {

	private int oscarWins;
	
	@ManyToMany(mappedBy="directors", cascade=CascadeType.ALL)
	private List<Movie> moviesdirected=null;
	
	private static final long serialVersionUID = 1L; 
	

	
	public List<Movie> getMovies() {
		if(moviesdirected == null) {
			moviesdirected = new ArrayList<Movie>();
	}
		return moviesdirected;
	}
	public void setMovies(List<Movie> moviesActed) {
		this.moviesdirected = moviesActed;
		for(Movie movie: moviesActed) {
			movie.getDirectors().add(this);
		}
	}

	public Director() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Director(String firstName, String lastName) {
		super(firstName, lastName);
		// TODO Auto-generated constructor stub
	}

   
}
//@Entity
//public class Director extends Person implements Serializable{
//	private int oscarWins;
//	@ManyToMany(mappedBy="directors", cascade=CascadeType.ALL)
//	@JsonIgnore
//	private List<Movie> moviesDirected;
//	private static final long serialVersionUID = 1L;
//	
//	public int getOscarWins() {
//		return oscarWins;
//	}
//
//	public void setOscarWins(int oscarWins) {
//		this.oscarWins = oscarWins;
//	}
//
//	public Director() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	
//	
//}
