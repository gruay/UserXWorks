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
	 * Tenir en compte aix�:
	 *  It�s not recommend to put any code inside the destroy(), because this method is not reliable. When your application server is force shutdown or be killed by command, the destroy() will not be called.
	 */
	@Override
	public void destroy() {
		m.close();
	}

	@Override
	public void init() {
		
		//Aix� �s mentre no provi de passar els par�metres b�, �s a dir, a trav�s de l'xml
		hostDB = "localhost";
		port = 27017;
		try {
			m = new MongoClient(hostDB, port);
			db = m.getDB(Global.DB_TFG);
		} catch (UnknownHostException e) {
			System.out.println("Peta la connexi� amb la DB");
			e.printStackTrace();
		}
	}

	@Override
	public String intercept(ActionInvocation inv) throws Exception {
		String className = inv.getAction().getClass().getName();
		System.out.println("Abans d'invocar l'acci�: " + className + " gestiono la DB");
		DBAction dba = (DBAction) inv.getAction();
		dba.setDb(db);
		String result = inv.invoke();
		System.out.println("Despr�s d'invocar l'acci�: " + className);
		return result;
	}

}
