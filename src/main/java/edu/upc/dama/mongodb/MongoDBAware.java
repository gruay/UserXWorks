package edu.upc.dama.mongodb;

import com.mongodb.DB;

public interface MongoDBAware {
	
	public void setDb(DB db);
	
	public DB getDb();
}
