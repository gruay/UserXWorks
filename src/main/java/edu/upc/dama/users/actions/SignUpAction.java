package edu.upc.dama.users.actions;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class SignUpAction extends ActionSupport implements MongoDBAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*private String hostDB;
	private int port;*/
	private DB db;
	private String username;
	private String password;
	
	public void validate() { //què hauria de posar si el validate falla? És que ho necessito saber per al jUnit
		if (username.length() == 0) {
			//el validate peta
		} 
		if (password.length() == 0) {
			//el validate peta
		}
		DBCollection coll = db.getCollection(Global.C_USERS);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, username);
		if (coll.find(q).hasNext()) {
			//el validate peta
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	

	@Override
	public String execute() throws Exception {
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.insert(new BasicDBObject(Global.A_USERNAME, username).append(Global.A_PASSWORD, password).append(Global.A_ACTIVE, true));
		return "success";
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DB getDb() {
		return db;
	}

}
