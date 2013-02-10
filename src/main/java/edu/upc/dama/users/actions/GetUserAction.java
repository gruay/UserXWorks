package edu.upc.dama.users.actions;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class GetUserAction extends ActionSupport implements MongoDBAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private String hostDB;
	private int port;*/
	private DB db;
	private String username;
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void validate() {
		if (username.length() == 0) {
			addFieldError("username", "Username is required");
		} 
	}
	
	/*public String getHostDB() {
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
	}*/


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String execute() throws Exception {
		//MongoClient m = new MongoClient(hostDB, port);
		//db = m.getDB(Global.DB_TFG);
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, username);
		if (!coll.find(q).hasNext()) {
			addActionError("No existeix cap usuari amb aquest username");
			return "error";
		}
		user = (User) coll.find(q).next();
		return "success";
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DB getDb() {
		return db;
	}
}
