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

public class SignUpActionTest {

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

	@Test // també n'hauria de fer un que fallés perquè l'usuari ja existeix
	public void testValidate() {
		SignUpAction tester = new SignUpAction();
		tester.setPassword("patata");
		tester.setUsername("david1991"); //que no existeixi
		tester.setDb(db);
		tester.validate();
		assertTrue(tester.getActionErrors().isEmpty());
	}

	@Test
	public void testValidateUserAlreadyExists() {
		SignUpAction tester = new SignUpAction();
		tester.setPassword("patata");
		tester.setUsername("david"); //que existeixi
		tester.setDb(db);
		tester.validate();
		assertFalse(tester.getActionErrors().isEmpty());
	}
	
	@Test
	public void testGetUsername() {
		SignUpAction tester = new SignUpAction();
		String username = new String("david");
		tester.setUsername(username);
		assertEquals(username, tester.getUsername());
	}

	@Test
	public void testSetUsername() {
		SignUpAction tester = new SignUpAction();
		String username = new String("david");
		tester.setUsername(username);
		assertEquals(username, tester.getUsername());
	}

	@Test
	public void testGetPassword() {
		SignUpAction tester = new SignUpAction();
		String pass = new String("patata");
		tester.setPassword(pass);
		assertEquals(pass, tester.getPassword());
	}

	@Test
	public void testSetPassword() {
		SignUpAction tester = new SignUpAction();
		String pass = new String("patata");
		tester.setPassword(pass);
		assertEquals(pass, tester.getPassword());
	}

	@Test
	public void testExecute() {
		SignUpAction tester = new SignUpAction();
		String username = "david" + System.nanoTime();
		tester.setUsername(username); //que no existeixi
		tester.setPassword("patata");
		tester.setDb(db);
		try {
			tester.execute();
		} catch (Exception e) {
			throw new RuntimeException("Falla l'execute");
		}
		DBCollection coll = db.getCollection(Global.C_USERS);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, username);
		assertTrue(coll.find(q).hasNext());
	}

	@Test
	public void testSetDb() {
		SignUpAction tester = new SignUpAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());	
	}

	@Test
	public void testGetDb() {
		SignUpAction tester = new SignUpAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

}
