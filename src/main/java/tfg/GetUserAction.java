package tfg;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionSupport;

public class GetUserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostDB;
	private int port;
	private DB db;
	private String username;
	
	public void validate() {
		if (username.length() == 0) {
			addFieldError("username", "User Name is required");
		} 
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


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String execute() throws Exception {
		MongoClient m = new MongoClient(hostDB, port);
		db = m.getDB(Global.DB_TFG);
		DBCollection coll = db.getCollection(Global.C_USERS);
		BasicDBObject q = new BasicDBObject(Global.A_USER, username);
		if (!coll.find(q).hasNext()) {
			addActionError("No existeix cap usuari amb aquest username");
			return "error";
		}
		//D'alguna manera carrego el resultat
		return "success";
	}
}
