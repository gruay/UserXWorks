package edu.upc.dama.users.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import edu.upc.dama.users.model.Global;

public class GoogleActionTest {

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
	public void test() {
		SignUpWithGoogleAction tester = new SignUpWithGoogleAction();
		Map<String, Object> session = new HashMap<String, Object>();
		tester.setSession(session);
		String res1 = "test1 declarat";
		try {
			res1 = tester.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Resultat primer tester " + res1);
		String url = tester.getAuthUrl();
		System.out.println(url);
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    String verifier = new String();
	    try {
			verifier = br.readLine();
			System.out.println("després de llegir");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SignUpWithGoogleCallbackAction tester2 = new SignUpWithGoogleCallbackAction();
	    tester2.setOauth_verifier(verifier);
	    tester2.setSession(session);
	    tester2.setDb(db);
	    String res2 = "test2 declarat";
	    try {
			res2 = tester2.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Resultat segon tester " + res2);
	}

}
