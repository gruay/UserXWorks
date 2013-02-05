package tfg;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionSupport;

public class SetUserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostDB;
	private int port;
	private DB db;
	private User user;
	
	public void validate() {
		// TODO: falta fer el mètode de validar
	}
	
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String execute() throws Exception {
		MongoClient m = new MongoClient(hostDB, port);
		db = m.getDB(Global.DB_TFG);
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, user.getUsername());
		if (!coll.find(q).hasNext()) {
			addActionError("No existeix l'usuari");
			return "error";
		}
		coll.update(q, user);
		return "success";
	}

}
