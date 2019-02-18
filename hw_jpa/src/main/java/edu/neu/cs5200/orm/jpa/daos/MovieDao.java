package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hsqldb.DatabaseManager;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.Person;

public class MovieDao{
	PreparedStatement pstmt;
	Statement stmt;
	private Connection conn;
	private DatabaseManager dbm;
	public MovieDao(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}
	
	
	public Movie findMovieById(int mid) {
		try {
			String qry = "select * from `movie` where `ID` ="+mid;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String title = rs.getString("TITLE");
			rs.close();	
			return new Movie(title);
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public void updateMovieById(int id, String title) {
		try {
			String cmd = "update `movie` set `TITLE`=?where `ID`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, title);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {

		}
	}

	public void deleteMovieById(int id) {
		try {
			String cmd = "delete from `movie` where `ID`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
		}
	}
	
	public List<Movie> findAllMovies() {
		try {
			String qry="select * from `movie`";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			List<Movie> movies = new ArrayList<>();
			while (rs.next()) {
				int mid = rs.getInt("ID"); 
				movies.add(findMovieById(mid));
			} 
			rs.close(); 
			return movies;
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}
	
	public void deleteAllMovies() {
		try {
			String cmd = "delete from `movie2actor`";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();
			cmd = "delete from `movie2director`";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();
			cmd = "delete from `movie`";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	public void createMovie(Movie movie) {
		try {
			String cmd = "insert into `movie` (`TITLE`) values(?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, movie.getTitle());	
			pstmt.executeUpdate();			
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public void createActor(Actor actor) {
		try {
			String cmd = "insert into `person` (`DTYPE`,`FIRSTNAME`,`LASTNAME`) values(?,?,?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, "actor");
			pstmt.setString(2, actor.getFirstName());
			pstmt.setString(3, actor.getLastName());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	public void createDirector(Director director) {
		try {
			String cmd = "insert into `person` (`DTYPE`,`FIRSTNAME`,`LASTNAME`) values(?,?,?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, "director");
			pstmt.setString(2, director.getFirstName());
			pstmt.setString(3, director.getLastName());
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public void createMovie_Actor(Movie movie, Actor actor) {
		try {
			String qry = "select * from `movie` where `title`="+"'"+movie.getTitle()+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			int mid = 0;
			if(rs.next()) {
			 mid = rs.getInt("ID");
			}
			rs.close();	
			
			String cmd = "SELECT * FROM `person` WHERE `FIRSTNAME`='"+actor.getFirstName()+"' AND LASTNAME='"+actor.getLastName()+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(cmd);
			int aid=0;
			if (rs.next()) {
				aid = rs.getInt("ID");
			} 
			rs.close(); 
						
			cmd = "INSERT INTO `movie2actor` (`moviesActed_ID`,`actors_ID`) VALUES (?,?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, mid);
			pstmt.setInt(2, aid);
			pstmt.executeUpdate();
			
					
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public void createMovie_Director(Movie movie, Director director) {
		try {
			String qry = "select * from `movie` where `title`="+"'"+movie.getTitle()+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			int mid = 0;
			if(rs.next()) {
			 mid = rs.getInt("ID");
			}
			rs.close();	
			
			String cmd = "SELECT * FROM `person` WHERE `FIRSTNAME`='"+director.getFirstName()+"' AND LASTNAME='"+director.getLastName()+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(cmd);
			int did=0;
			if (rs.next()) {
				did = rs.getInt("ID");
			} 
			rs.close(); 
						
			cmd = "INSERT INTO `movie2director` (`moviesdirected_ID`,`directors_ID`) VALUES (?,?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, mid);
			pstmt.setInt(2, did);
			pstmt.executeUpdate();
			
					
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	public List<Actor> RetrieveActorsOfMovie(Movie movie){
		try {
		String qry="select p.FIRSTNAME,p.LASTNAME from movie m, person p, movie2actor ma where m.TITLE='"+movie.getTitle()+ "' and m.ID=ma.moviesActed_ID and ma.actors_ID=p.ID";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(qry);
		List<Actor> actors = new ArrayList<>();
		while (rs.next()) {
			String first = rs.getString("FIRSTNAME"); 
			String last = rs.getString("LASTNAME"); 
			actors.add(new Actor(first,last));
		} 
		rs.close(); 
		return actors;
		}catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public List<Director> RetrieveDirectorsOfMovie(Movie movie){
		try {
		String qry="select p.FIRSTNAME,p.LASTNAME from movie m, person p, movie2director md where m.TITLE='"+movie.getTitle()+ "' and m.ID=md.moviesdirected_ID and md.directors_ID=p.ID";
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(qry);
		List<Director> directors = new ArrayList<>();
		while (rs.next()) {
			String first = rs.getString("FIRSTNAME"); 
			String last = rs.getString("LASTNAME"); 
			directors.add(new Director(first,last));
		} 
		rs.close(); 
		return directors;
		}catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public List<Movie> findMoviesByActor(Actor actor){
		try {
			String qry="select m.TITLE from movie m, movie2actor ma, person p where m.ID=ma.moviesActed_ID and p.ID=ma.actors_ID "
					+ "and p.FIRSTNAME='"+actor.getFirstName()+"' and p.LASTNAME='"+actor.getLastName()+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			List<Movie> movies = new ArrayList<>();
			while (rs.next()) {
				String title = rs.getString("TITLE");
				movies.add(new Movie(title));
			} 
			rs.close(); 
			return movies;
			}catch(SQLException e) {
				throw new RuntimeException("…", e);
			}
	}
	
	
	public List<Movie> findMoviesByDirector(Director director){
		try {
			String qry="select m.TITLE from movie m, movie2director md, person p where m.ID=md.moviesdirected_ID and p.ID=md.directors_ID "
					+ "and p.FIRSTNAME='"+director.getFirstName()+"' and p.LASTNAME='"+director.getLastName()+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			List<Movie> movies = new ArrayList<>();
			while (rs.next()) {
				String title = rs.getString("TITLE");
				movies.add(new Movie(title));
			} 
			rs.close(); 
			return movies;
			}catch(SQLException e) {
				throw new RuntimeException("…", e);
			}
	}
	
	
	
	public void test() {
		deleteAllMovies();
		Movie movie1 = new Movie("Blade Runner");
		createMovie(movie1);
		Actor actor1 = new Actor("Harrison","Ford");
		createActor(actor1);
		Actor actor2= new Actor("Rutger","Hauer");
		createActor(actor2);
		Director director1 = new Director("Ridley","Scott");
		createDirector(director1);
		createMovie_Actor(movie1,actor1);
		createMovie_Actor(movie1,actor2);
		createMovie_Director(movie1,director1);
		
		
		Movie movie2 = new Movie("Raiders of The Lost Ark");
		createMovie(movie2);
		Actor actor3= new Actor("Karen","Allen");
		createActor(actor3);
		Director director2 = new Director("Steven","Spielberg");
		createDirector(director2);
		createMovie_Actor(movie2,actor1);
		createMovie_Actor(movie2,actor3);
		createMovie_Director(movie2,director2);
		
		
		Movie movie3 = new Movie("Close Encounters of the Third Kind");
		createMovie(movie3);
		Actor actor4 = new Actor("Richard","Dreyfus");
		createActor(actor4);
		Actor actor5 = new Actor("Melinda","Dillon");
		createActor(actor5);
		createMovie_Actor(movie3,actor4);
		createMovie_Actor(movie3,actor5);
		createMovie_Director(movie3,director2);
		

		System.out.println(movie1.getTitle());
		List<Actor> actors_movie1 = RetrieveActorsOfMovie(movie1);
		for(Actor actor:actors_movie1) {
			System.out.println(actor.getFirstName()+" "+actor.getLastName());
		}
		
		List<Director> directors_movie1 = RetrieveDirectorsOfMovie(movie1);
		for(Director director:directors_movie1) {
			System.out.println(director.getFirstName()+" "+director.getLastName());
		}
		
		
		System.out.println(movie2.getTitle());
		List<Actor> actors_movie2 = RetrieveActorsOfMovie(movie2);
		for(Actor actor:actors_movie2) {
			System.out.println(actor.getFirstName()+" "+actor.getLastName());
		}
		
		List<Director> directors_movie2 = RetrieveDirectorsOfMovie(movie2);
		for(Director director:directors_movie2) {
			System.out.println(director.getFirstName()+" "+director.getLastName());
		}
		
		
		System.out.println(movie3.getTitle());
		List<Actor> actors_movie3 = RetrieveActorsOfMovie(movie3);
		for(Actor actor:actors_movie3) {
			System.out.println(actor.getFirstName()+" "+actor.getLastName());
		}
		
		List<Director> directors_movie3 = RetrieveDirectorsOfMovie(movie3);
		for(Director director:directors_movie3) {
			System.out.println(director.getFirstName()+" "+director.getLastName());
		}
		
			
		
		ActorDao actorDao = new ActorDao(conn, dbm);
		Actor actor_1 = actorDao.findActorByName("Harrison", "Ford");
		System.out.println(actor_1.getFirstName()+" "+actor_1.getLastName());
		List<Movie> movies1 = findMoviesByActor(actor_1);
		for(Movie movie:movies1) {
			System.out.println(movie.getTitle());
		}
		
		
		DirectorDao directorDao = new DirectorDao(conn, dbm);
		Director director_1 = directorDao.findDirectorByName("Steven", "Spielberg ");
		System.out.println(director_1.getFirstName()+" "+director_1.getLastName());
		List<Movie> movies2 = findMoviesByDirector(director_1);
		for(Movie movie:movies2) {
			System.out.println(movie.getTitle());
		}
		
	}
	
}
