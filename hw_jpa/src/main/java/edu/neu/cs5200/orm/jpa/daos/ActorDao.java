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

public class ActorDao{
	PreparedStatement pstmt;
	Statement stmt;
	private Connection conn;
	private DatabaseManager dbm;
	public ActorDao(Connection conn, DatabaseManager dbm) {
		this.conn = conn;
		this.dbm = dbm;
	}

	public void createActor(String firstName, String lastName) {
		try {
			String cmd = "insert into `person` (`DTYPE`,`FIRSTNAME`,`LASTNAME`) values('actor',?, ?)";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, firstName);		
			pstmt.setString(2, lastName);
			pstmt.executeUpdate();
		} catch(SQLException e) {
//			throw new RuntimeException("…", e);
		}
	}

	public Actor findActorById(int aid) {
		try {
			String qry = "select * from `person` where `DTYPE`='actor' and `id` ="+aid;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String firstName = rs.getString("FIRSTNAME");
			String lastName = rs.getString("LASTNAME");
			rs.close();	
			return new Actor(firstName,lastName);
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}

	
	public Actor findActorByName(String firstName, String lastName) {
		try {
			String qry = "select * from `person` where FIRSTNAME='"+firstName+"' and LASTNAME ='"+lastName+"'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String first = rs.getString("FIRSTNAME");
			String last = rs.getString("LASTNAME");
			rs.close();	
			return new Actor(first,last);
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}
	
	
	public Actor findFirstActor() {
		try {
			String qry = "select * from `person` where `DTYPE`='actor' LIMIT 1";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			if (!rs.next()) return null;
			String firstName = rs.getString("FIRSTNAME");
			String lastName = rs.getString("LASTNAME");
			rs.close();	
			return new Actor(firstName,lastName);
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}

	public List<Actor> findAllActors() {
		try {
			String qry="select * from `person` where `DTYPE`='actor'";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			List<Actor> actors = new ArrayList<>();
			while (rs.next()) {
				int aid = rs.getInt("ID"); 
				actors.add(findActorById(aid));
			} 
			rs.close(); 
			return actors;
		} catch(SQLException e) {
			throw new RuntimeException("…");
		}
	}

	
	
	
	
	public void updateActor(String firstName, String lastName, String firstName2, String lastName2) {
		try {
			String cmd = "update `person` set `FIRSTNAME`=?, `LASTNAME`=? where `FIRSTNAME`=? and `LASTNAME`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, firstName2);
			pstmt.setString(4, lastName2);
			pstmt.executeUpdate();
		} catch(SQLException e) {

		}
	}

	public void deleteActor(String firstName, String lastName) {
		try {
			String cmd = "delete from `person` where `FIRSTNAME`=? and `LASTNAME`=?";
			pstmt = conn.prepareStatement(cmd);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.executeUpdate();
		} catch(SQLException e) {
		}
	}

	public void deleteAllActors() {
		try {
			String cmd = "delete from `person` where `DTYPE`='actor'";
			pstmt = conn.prepareStatement(cmd);
			pstmt.executeUpdate();
		} catch(SQLException e) {

		}
	}

	public void test() {
		deleteAllActors();
		createActor("Sigorney","Weaver");
		createActor("Dan","Creg");
		createActor("Jim","Carrey");
		Actor firstActor = findFirstActor();
		System.out.println(firstActor.getFirstName()+" "+firstActor.getLastName());
		
		List<Actor> allActors = findAllActors();
		for(Actor actor:allActors) {
			System.out.println(actor.getFirstName()+" "+actor.getLastName());
		}
		updateActor("Dan","Creg","Daniel", "Craig");
		deleteActor("Jim","Carrey");
		allActors = findAllActors();
		for(Actor actor:allActors) {
			System.out.println(actor.getFirstName()+" "+actor.getLastName());
		}
	}
}

