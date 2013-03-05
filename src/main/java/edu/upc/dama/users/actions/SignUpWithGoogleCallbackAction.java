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

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.mongodb.MongoDBAware;
import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.User;

public class SignUpWithGoogleCallbackAction extends ActionSupport implements
		SessionAware, MongoDBAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DB db;
	private Map<String, Object> session;
	private String key;
	private String secret;
	private String oauth_token;
	private String oauth_verifier;
	
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

		OAuthRequest request = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v1/userinfo?alt=json"); //no estic segur que funcioni aquest enlla� --> https://www.googleapis.com/plus/v1/people/me
		service.signRequest(accessToken, request);
		Response response = request.send();
		if (!response.isSuccessful()) {
			addActionError("Not authorized");
			return "error";
		}
		JSONObject json = new JSONObject(response.getBody());
		String email = json.getString("email");
		String name = json.getString("name");
		String id = json.getString("id");
		String pictureUrl = json.getString("picture");
		String sexe = json.getString("gender");
		String sex = new String();
		if (sexe.equals("male")) {
			sex = "H";
		}
		else {
			sex = "D";
		}
		
		User usr = new User();
		usr.setImatge(pictureUrl);
		usr.setNom(name);
		usr.setSexe(sex);
		usr.setUsername(email);
		usr.setGoogle(id);
		
		DBCollection coll = db.getCollection(Global.C_USERS);
		coll.setObjectClass(User.class);
		coll.insert(usr);
		
		return "success";
	}
	
}
