package edu.upc.dama.users.actions;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class GetUsersAction extends ActionSupport implements MongoDBAware{

	private static final long serialVersionUID = 1L;
	private DB db;
	private String username;
	private List<DBObject> users;
	private int option; // 0: tots, 1: actius, 2: inactius 
	
	public List<DBObject> getUsers() {
		return users;
	}

	public void setUsers(List<DBObject> users) {
		this.users = users;
	}

	public void validate() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOption() {
		return option;
	}

	public void setOption(int option) {
		this.option = option;
	}

	@Override
	public String execute() throws Exception {
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject();
		switch (option) {
		case 1:
			q = new BasicDBObject(Global.A_ACTIVE, true);
			break;
		case 2:
			q = new BasicDBObject(Global.A_ACTIVE, false);
			break;
		default:
			break;
		}
		users = coll.find(q).toArray();
		return "success";
	}

	public void setDb(DB db) {
		this.db = db;
	}

	public DB getDb() {
		return db;
	}
}
