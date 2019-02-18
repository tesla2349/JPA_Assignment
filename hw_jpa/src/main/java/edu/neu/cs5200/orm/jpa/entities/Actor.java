package edu.neu.cs5200.orm.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity

public class Actor extends Person implements Serializable {

	private int oscarNominations;
	
	@ManyToMany(mappedBy="actors", cascade=CascadeType.ALL)
	private List<Movie> moviesActed=null;
	
	private static final long serialVersionUID = 1L; 
	
	public int getOscarNominations() {
		return this.oscarNominations;
	}

	public void setOscarNominations(int oscarNominations) {
		this.oscarNominations = oscarNominations;
	}   
	
	public List<Movie> getMovies() {
		if(moviesActed == null) {
			moviesActed = new ArrayList<Movie>();
	}
		return moviesActed;
	}
	public void setMovies(List<Movie> moviesActed) {
		this.moviesActed = moviesActed;
		for(Movie movie: moviesActed) {
			movie.getActors().add(this);
		}
	}

	public Actor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Actor(String firstName, String lastName) {
		super(firstName, lastName);
		// TODO Auto-generated constructor stub
	}

   
}

//@Entity
//public class Actor extends Person implements Serializable{
//	private int oscarNominations;
//	@ManyToMany(mappedBy="actors", cascade=CascadeType.ALL)
//	@JsonIgnore
//	private List<Movie> moviesActed;
//	
//	private static final long serialVersionUID = 1L;
//
//	public int getOscarNominations() {
//		return oscarNominations;
//	}
//
//	public void setOscarNominations(int oscarNominations) {
//		this.oscarNominations = oscarNominations;
//	}
//
//	public Actor() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Actor(int oscarNominations) {
//		super();
//		this.oscarNominations = oscarNominations;
//	}
//}
