package edu.upc.dama.users.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.json.JSONObject;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class ConnectWithTwitterCallbackAction extends ActionSupport implements
		SessionAware, MongoDBAware {

	/**
	 * Aquesta Action és la que s'hauria de cridar un cop l'usuari ha autoritzat la nostra aplicació.
	 * A la session hi hauria d'haver la requestToken, l'objecte Twitter i hi posarem l'accessToken.
	 * Llavors amb tot això intentarem aconseguir l'id de Twitter de l'usuari per tal de poder-lo emmagatzemar a la BD.
	 * L'user ha de ser el de la crida anterior, no sé si l'hauria de posar a la session o no.
	 * Potser hauria de validar que l'usuari no estigui connectat amb Twitter?
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private User user; // l'he d'haver rebut de l'action anterior.
	private DB db;
	private String oauth_token;
	private String oauth_verifier;
	private String key;
	private String secret;
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	
	@Override
	public String execute() throws Exception {
		
		if (session.containsKey(Global.A_ACCESS_TOKEN) && session.get(Global.A_ACCESS_TOKEN) != null) {
			return "success";
		}
		
		
		Token requestToken = (Token) session.get(Global.A_REQUEST_TOKEN);
		if (requestToken == null) {
			addActionError("The requestToken is null");
			return "errror";
		}
		OAuthService service = (OAuthService) session.get(Global.A_SERVICE);
		Token accessToken = service.getAccessToken(requestToken, new Verifier(this.getOauth_verifier()));
		session.put(Global.A_ACCESS_TOKEN, accessToken);
		this.setKey(accessToken.getToken());
		this.setSecret(accessToken.getSecret());
		
		OAuthRequest request = new OAuthRequest(Verb.GET,  "https://api.twitter.com/1.1/account/verify_credentials.json");
		service.signRequest(accessToken, request);
		Response response = request.send();
		if (!response.isSuccessful()) {
			addActionError("Not authorized");
			return "error";
		}
		JSONObject json = new JSONObject(response.getBody());
		
		long userId = json.getLong("id");
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		user.setTwitter(String.valueOf(userId));
		BasicDBObject q = new BasicDBObject(Global.A_USERNAME, user.getUsername());
		coll.update(q, user);
		return "success";
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getOauth_token() {
		return oauth_token;
	}


	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}


	public String getOauth_verifier() {
		return oauth_verifier;
	}


	public void setOauth_verifier(String oauth_verifier) {
		this.oauth_verifier = oauth_verifier;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Map<String, Object> getSession() {
		return session;
	}


	public void setDb(DB db) {
		this.db = db;
	}


	public DB getDb() {
		return db;
	}
	
	
}
