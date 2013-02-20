package edu.upc.dama.users.actions;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class InactivateUserAction extends ActionSupport implements MongoDBAware {

	private static final long serialVersionUID = 1L;
	private DB db;
	private String username;
	
	public void validate() {
		if (username.length() == 0) {
			addActionError("Username is empty");
		}
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, username);
		if (!coll.find(q).hasNext()) {
			addActionError("The user doesn't exist");
		}
		User usr = (User) coll.find(q).next();
		if (!usr.isActive()) {
			addActionError("The user is already inactive");
		}
	}

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
