package tfg;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionSupport;

public class SignUpAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostDB;
	private int port;
	private DB db;
	private String username;
	private String password;
	private String uTwitter;
	
	
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getuTwitter() {
		return uTwitter;
	}


	public void setuTwitter(String uTwitter) {
		this.uTwitter = uTwitter;
	}
	

	@Override
	public String execute() throws Exception {
		MongoClient m = new MongoClient(hostDB, port);
		db = m.getDB(Global.DB_TFG);
		DBCollection coll = db.getCollection(Global.C_USERS);
		BasicDBObject q = new BasicDBObject(Global.A_USER, username);
		if (coll.find(q).hasNext()) {
			addActionError("Ja existeix un usuari amb aquest nom.");
			return "error";
		}
		coll.insert(new BasicDBObject(Global.A_USER, username).append(Global.A_PASSWORD, password).append(Global.A_TWITTER, uTwitter));
		return "success";
	}

}
