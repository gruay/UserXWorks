package edu.upc.dama.users.actions;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import edu.upc.dama.users.model.Global;

public class InactivateUserActionTest {

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
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsername() {
		InactivateUserAction tester = new InactivateUserAction();
		String username = new String("david");
		tester.setUsername(username);
		assertEquals(username, tester.getUsername());
	}

	@Test
	public void testSetUsername() {
		InactivateUserAction tester = new InactivateUserAction();
		String username = new String("david");
		tester.setUsername(username);
		assertEquals(username, tester.getUsername());
	}

	@Test
	public void testExecute() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDb() {
		InactivateUserAction tester = new InactivateUserAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

	@Test
	public void testGetDb() {
		InactivateUserAction tester = new InactivateUserAction();
		tester.setDb(db);
		assertEquals(db, tester.getDb());
	}

}
