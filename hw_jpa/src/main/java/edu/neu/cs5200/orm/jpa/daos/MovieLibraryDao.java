package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.DatabaseManager;

import edu.neu.cs5200.orm.jpa.entities.Actor;
import edu.neu.cs5200.orm.jpa.entities.Director;
import edu.neu.cs5200.orm.jpa.entities.Movie;
import edu.neu.cs5200.orm.jpa.entities.MovieLibrary;

public class MovieLibraryDao {
	
	PreparedStatement pstmt;
	Statement stmt;
	private Connection conn;
	private DatabaseManager dbm;
	public MovieLibraryDao(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}
	
	public MovieLibrary findMovieLibraryById(int mid) {
		try {
			String qry = "select * from `movielibrary` where `ID` ="+mid;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String name = rs.getString("NAME");
			rs.close();	
			return new MovieLibrary(name);
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public void updateMovieLibraryById(int id, String name) {
		try {
			String cmd = "update `movielibrary` set `NAME`=?where `ID`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, name);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {

		}
	}

	public void deleteMovieById(int id) {
		try {
			String cmd = "delete from `movielibrary` where `ID`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch(SQLException e) {
		}
	}
	
	public List<MovieLibrary> findAllMovies() {
		try {
			String qry="select * from `movielibrary`";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			List<MovieLibrary> movielibraries = new ArrayList<>();
			while (rs.next()) {
				int mid = rs.getInt("ID"); 
				movielibraries.add(findMovieLibraryById(mid));
			} 
			rs.close(); 
			return movielibraries;
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}
	
	public void deleteAllMovieLibraries() {
		try {
			
			String cmd = "delete from `movielibrary`";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();		
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	public void createMovieLibrary(MovieLibrary movieLibrary) {
		try {
			String cmd = "insert into `movielibrary` (`NAME`) values(?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, movieLibrary.getName());	
			pstmt.executeUpdate();			
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	
	public void createMovie(Movie movie, MovieLibrary movielibrary) {
		try {
			String qry = "select * from `movielibrary` where `NAME`="+"'"+movielibrary.getName()+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			int mlid = 0;
			if(rs.next()) {
			 mlid = rs.getInt("ID");
			}
			rs.close();
			
			String cmd = "insert into `movie` (`TITLE`,`LIBRARY_ID`) values(?,?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, movie.getTitle());
			pstmt.setInt(2, mlid);
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
	
	public List<Movie> findMovieByLibrary(MovieLibrary movielibrary) {
		try {
		String qry="select * from movie m, movielibrary ml where ml.ID=m.LIBRARY_ID";
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
	
	public void test() {
		deleteAllMovieLibraries();
		
		MovieLibrary movielibrary = new MovieLibrary("Favorite Movies");
		createMovieLibrary(movielibrary);
		
		Movie movie1 = new Movie("Star Wars A New Hope");
		createMovie(movie1,movielibrary);
		Movie movie2 = new Movie("The Revanant");
		createMovie(movie2,movielibrary);
		
		Actor actor1 = new Actor("Mark","Hamill");
		createActor(actor1);	
		Actor actor2= new Actor("Carrie","Fisher");
		createActor(actor2);
		
		Director director1 = new Director("George","Lucas");
		createDirector(director1);
		
		createMovie_Actor(movie1,actor1);
		createMovie_Actor(movie1,actor2);
		createMovie_Director(movie1,director1);
		
		Actor actor3= new Actor("Leonardo","DiCaprio");
		createActor(actor3);
		Actor actor4= new Actor("Tom","Hardy");
		createActor(actor4);
		Director director2 = new Director("Alejandro","Inarritu");
		createDirector(director2);
		createMovie_Actor(movie2,actor3);
		createMovie_Actor(movie2,actor4);
		createMovie_Director(movie2,director2);
		
		
		
		
		System.out.println(movielibrary.getName());
		
		List<Movie> movies = findMovieByLibrary(movielibrary);
		for(Movie movie:movies) {
			System.out.println(movie.getTitle());
			
			List<Actor> actors = new ArrayList<>();
			actors = RetrieveActorsOfMovie(movie);
			for(Actor actor:actors) {
				System.out.println(actor.getFirstName()+" "+actor.getLastName());
			}
			List<Director> directors = new ArrayList<>();
			directors = RetrieveDirectorsOfMovie(movie);
			for(Director director:directors) {
				System.out.println(director.getFirstName()+" "+director.getLastName());
			}
		}
	}


	
}
