/**
 * LGPL
 */
package edu.upc.dama.mongodb;

import java.net.UnknownHostException;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class MongoDBManagerInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostDB = "localhost";
	private int port = 27017;
	private DB db;
	private String DBname;
	private MongoClient m;
	
	public String getHostDB() {
		return hostDB;
	}

	public void setHostDB(String hostDB) {
		this.hostDB = hostDB;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}
	
	public String getDBname() {
		return DBname;
	}

	public void setDBname(String DBname) {
		this.DBname = DBname;
	}

	/*
	 * Tenir en compte això:
	 *  It’s not recommend to put any code inside the destroy(), because this method is not reliable. When your application server is force shutdown or be killed by command, the destroy() will not be called.
	 */
	public void destroy() {
		m.close();
	}

	public void init() {
		
		try {
			m = new MongoClient(hostDB, port);
			db = m.getDB(DBname);
		} catch (UnknownHostException e) {
			throw new RuntimeException("unavailable database", e);
		}
	}

	public String intercept(ActionInvocation inv) throws Exception {
		if (inv.getAction() instanceof MongoDBAware) {
			MongoDBAware dba = (MongoDBAware) inv.getAction();
			dba.setDb(db);
		}
		return inv.invoke();
	}

}
