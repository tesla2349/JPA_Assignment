package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.DatabaseManager;

import edu.neu.cs5200.orm.jpa.entities.Movie;

public class TestDao {

	public static void main(String[] args) {

		Connection conn;
		DatabaseManager dbm = null;
		ActorDao actorDao;
		DirectorDao directorDao;
		MovieDao movieDao;
		MovieLibraryDao movieLibraryDao;
		  try {
			    String url = "jdbc:mysql://localhost:3306/hw_jpa";
			    conn = DriverManager.getConnection(url, "marui", "Aa84061776");
			    conn.setAutoCommit(false);
			    actorDao = new ActorDao(conn, dbm);
			    actorDao.test();
			    directorDao = new DirectorDao(conn, dbm);
			    directorDao.test();    
			    movieDao = new MovieDao(conn, dbm);
			    movieDao.test();
			    movieLibraryDao = new MovieLibraryDao(conn, dbm);
			    movieLibraryDao.test();
			    
			  } catch(SQLException e) {
				throw new RuntimeException("…", e);
			  }

	}
	

}
