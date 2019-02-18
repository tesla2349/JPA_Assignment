package edu.neu.cs5200.orm.jpa.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.DatabaseManager;

import edu.neu.cs5200.orm.jpa.entities.Director;

public class DirectorDao {
	PreparedStatement pstmt;
	Statement stmt;
	private Connection conn;
	private DatabaseManager dbm;
	public DirectorDao(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}

	public void createDirector(String firstName, String lastName) {
		try {
			String cmd = "insert into `person` (`DTYPE`,`FIRSTNAME`,`LASTNAME`) values('director',?, ?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, firstName);		
			pstmt.setString(2, lastName);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}

	public Director findDirectorById(int did) {
		try {
			String qry = "select * from `person` where `DTYPE`='director' and `ID` ="+did;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String firstName = rs.getString("FIRSTNAME");
			String lastName = rs.getString("LASTNAME");
			rs.close();	
			return new Director(firstName,lastName);
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}

	public Director findDirectorByName(String firstName, String lastName) {
		try {
			String qry = "select * from `person` where FIRSTNAME='"+firstName+"' and LASTNAME ='"+lastName+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String first = rs.getString("FIRSTNAME");
			String last = rs.getString("LASTNAME");
			rs.close();	
			return new Director(first,last);
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}
	
	public Director findFirstDirector() {
		try {
			String qry = "select * from `person` where `DTYPE`='director' LIMIT 1";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String firstName = rs.getString("FIRSTNAME");
			String lastName = rs.getString("LASTNAME");
			rs.close();	
			return new Director(firstName,lastName);
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	public List<Director> findAllDirectors() {
		try {
			String qry="select * from `person` where `DTYPE`='director'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			List<Director> directors = new ArrayList<>();
			while (rs.next()) {
				int did = rs.getInt("ID"); 
				directors.add(findDirectorById(did));
			} 
			rs.close(); 
			return directors;
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}}

	public void updateDirector(String firstName, String lastName, String firstName2, String lastName2) {
		try {
			String cmd = "update `person` set `FIRSTNAME`=?, `LASTNAME`=? where `FIRSTNAME`=? and `LASTNAME`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, firstName2);
			pstmt.setString(4, lastName2);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}

	public void deleteDirector(String firstName, String lastName) {
		try {
			String cmd = "delete from `person` where `FIRSTNAME`=? and `LASTNAME`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}
	
	public void deleteAllDirectors() {
		try {
			String cmd = "delete from `director`";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();
			cmd = "delete from `person` where `DTYPE`='director'";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException("…", e);
		}
	}

	public void test() {
		deleteAllDirectors();
		createDirector("Jimmy","Camaron");
		createDirector("Steven","Spielberg");
		createDirector("Ron","Howard");
		Director firstDirector = findFirstDirector();
		System.out.println(firstDirector.getFirstName()+" "+firstDirector.getLastName());
		List<Director> allDirectors = findAllDirectors();
		for(Director director:allDirectors) {
			System.out.println(director.getFirstName()+" "+director.getLastName());
		}
		updateDirector("Jimmy","Camaron","James","Camaron");
		deleteDirector("Ron","Howard");
		allDirectors = findAllDirectors();
		for(Director actor:allDirectors) {
			System.out.println(actor.getFirstName()+" "+actor.getLastName());
		}
	}
}
