package edu.upc.dama.mongodb;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import edu.upc.dama.users.model.Global;

public class SessionManagementWithMongoDBInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostDB = "localhost";
	private int port = 27017;
	private DB db;
	private String DBname;
	private MongoClient m;
	private String DBCollectionName = "session";
	private DBCollection coll;

	public String getHostDB() {
		return hostDB;
	}

	public DBCollection getColl() {
		return coll;
	}

	public void setColl(DBCollection coll) {
		this.coll = coll;
	}

	public String getDBCollectionName() {
		return DBCollectionName;
	}

	public void setDBCollectionName(String dBCollectionName) {
		DBCollectionName = dBCollectionName;
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

	public void setDBname(String dBname) {
		DBname = dBname;
	}

	public MongoClient getM() {
		return m;
	}

	public void setM(MongoClient m) {
		this.m = m;
	}

	public void destroy() {
		m.close();
	}

	public void init() {
		try {
			m = new MongoClient(hostDB, port);
			db = m.getDB(DBname);
			coll = db.getCollection(DBCollectionName);
		} catch (UnknownHostException e) {
			throw new RuntimeException("unavailable database", e);
		}
	}

	public String intercept(ActionInvocation inv) throws Exception {
		Map<String, Object> session = new HashMap<String, Object>();
		String sid = new String();
		if (inv.getAction() instanceof SessionAware) {
			SessionAware sa = (SessionAware) inv.getAction();
			sid = (String) inv.getInvocationContext().getParameters().get("userid"); // TODO He de tenir en compte que està guardat com a userid
			
			BasicDBObject q = new BasicDBObject(Global.A_SID, sid);
			BasicDBObject proj = new BasicDBObject();
			proj.put(Global.A_SESSION, 1);
			proj.put(Global.A_ID, 0);
			
			DBCursor cur = coll.find(q, proj);
			if (!cur.hasNext()) {
				// TODO Que hauria de fer res?
				// TODO Suposo que l'hauria de crear
			}
			else {
				DBObject ses = cur.next(); // Això és l'element session dintre l'estructura {sid:123, session:{accTok:"foo", reqTok:"bar"}}
				DBObject sess = (DBObject) ses.get(Global.A_SESSION);
				session = sess.toMap();
			}
			
			sa.setSession(session);
			
		}
		
		String result = inv.invoke();
		
		if (!result.equals("error") && inv.getAction() instanceof SessionAware) {
			BasicDBObject q = new BasicDBObject(Global.A_SID, sid);
			BasicDBObject set = new BasicDBObject("$set",new BasicDBObject().append(Global.A_SESSION, new BasicDBObject(session)));
			set.append("$set", new BasicDBObject().append(Global.A_SESSION, new java.util.Date()));
			coll.update(q, set, true, false);
			coll.ensureIndex(new BasicDBObject(Global.A_TS, 1), new BasicDBObject("expireAfterSeconds", 3600)); //TODO Aquí es defineix el temps de la sesion, si l'índex no estigués creat, es crearia aquí
		}
		
		return result;
	}

}
