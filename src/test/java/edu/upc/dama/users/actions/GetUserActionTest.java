package edu.upc.dama.users.actions;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class GetUserActionTest {

	private static DB db;
	private static MongoClient m;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			m = new MongoClient("localhost", 27017);
			db = m.getDB(Global.DB_TFG);
		} catch (UnknownHostException e) {
			throw new RuntimeException("unavailable database", e);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		m.close();
	}

	@Test
	public void testValidate() {
		GetUserAction tester = new GetUserAction();
		tester.setUsername("david"); //que existeixi i sigui inactiu
		tester.setDb(db);
		tester.validate();
		assertTrue(tester.getActionErrors().isEmpty());
	}

	@Test
	public void testValidateUserDoesNotExist() {
		GetUserAction tester = new GetUserAction();
		tester.setUsername("david1991"); //que no existeixi
		tester.setDb(db);
		tester.validate();
		assertFalse(tester.getActionErrors().isEmpty());
	}

	@Test
	public void testGetUsername() {
		GetUserAction tester = new GetUserAction();
		String username = new String("david");
		tester.setUsername(username);
		assertEquals(username, tester.getUsername());
	}

	@Test
	public void testSetUsername() {
		GetUserAction tester = new GetUserAction();
		String username = new String("david");
		tester.setUsername(username);
		assertEquals(username, tester.getUsername());
	}

	@Test
	public void testExecute() {
		GetUserAction tester = new GetUserAction();
		tester.setUsername("david"); //que existeixi 
		tester.setDb(db);
		try {
			tester.execute();
		} catch (Exception e) {
			throw new RuntimeException("Falla l'execute");
		}
		User usr = tester.getUser();
		assertTrue(usr.getUsername().equals("david"));
	}

	@Test
	public void testSetDb() {
		GetUserAction tester = new GetUserAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

	@Test
	public void testGetDb() {
		GetUserAction tester = new GetUserAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

}
