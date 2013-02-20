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

public class SetUserActionTest {

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

	/*@Test
	public void testValidate() {
		
	}*/

	@Test
	public void testGetUser() {
		SetUserAction tester = new SetUserAction();
		User usr = new User();
		tester.setUser(usr);
		assertEquals(usr, tester.getUser());
	}

	@Test
	public void testSetUser() {
		SetUserAction tester = new SetUserAction();
		User usr = new User();
		tester.setUser(usr);
		assertEquals(usr, tester.getUser());	}

	@Test
	public void testExecute() {
		SetUserAction tester = new SetUserAction();
		User usr = new User();
		usr.setUsername("marc"); //que existeixi
		String nom = String.valueOf(System.nanoTime());
		usr.setNom(nom);
		tester.setDb(db);
		try {
			tester.execute();
		} catch (Exception e) {
			throw new RuntimeException("Falla l'execute");
		}
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		User usr2 = (User) coll.find(new BasicDBObject(Global.A_NOM, nom)).next();
		assertTrue(nom.equals(usr2.get(Global.A_NOM)));	
	}

	@Test
	public void testSetDb() {
		SetUserAction tester = new SetUserAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

	@Test
	public void testGetDb() {
		SetUserAction tester = new SetUserAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

}
