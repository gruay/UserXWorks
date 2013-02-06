package tfg;

import com.mongodb.DB;
import com.opensymphony.xwork2.ActionSupport;

public abstract class DBAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DB db;
	
	public DB getDb() {
		return db;
	}
	public void setDb(DB db) {
		this.db = db;
	}
	
	
	
}
