package tfg;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class DBManagerInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String hostDB;
	private int port;
	private DB db;
	private MongoClient m;
	
	/*
	 * Tenir en compte això:
	 *  It’s not recommend to put any code inside the destroy(), because this method is not reliable. When your application server is force shutdown or be killed by command, the destroy() will not be called.
	 */
	@Override
	public void destroy() {
		m.close();
	}

	@Override
	public void init() {
		
		//Això és mentre no provi de passar els paràmetres bé, és a dir, a través de l'xml
		hostDB = "localhost";
		port = 27017;
		try {
			m = new MongoClient(hostDB, port);
			db = m.getDB(Global.DB_TFG);
		} catch (UnknownHostException e) {
			System.out.println("Peta la connexió amb la DB");
			e.printStackTrace();
		}
	}

	@Override
	public String intercept(ActionInvocation inv) throws Exception {
		String className = inv.getAction().getClass().getName();
		System.out.println("Abans d'invocar l'acció: " + className + " gestiono la DB");
		DBAction dba = (DBAction) inv.getAction();
		dba.setDb(db);
		String result = inv.invoke();
		System.out.println("Després d'invocar l'acció: " + className);
		return result;
	}

}
