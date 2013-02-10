package edu.upc.dama.users.actions;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class InactivateUserAction extends ActionSupport implements MongoDBAware {

	/**
	 *  Fer una que sigui inhabilitar
	 */
	private static final long serialVersionUID = 1L;
	/*private String hostDB;
	private int port;*/
	private DB db;
	private String username;
	
	public void validate() {
		// TODO: falta fer el mètode de validar
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
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, username);
		if (!coll.find(q).hasNext()) {
			addActionError("No existeix l'usuari");
			return "error";
		}
		User usr = (User) coll.find(q).next();
		usr.setActive(false);
		coll.save(usr);
		return "success";
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DB getDb() {
		return db;
	}

}
