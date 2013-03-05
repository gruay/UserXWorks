package edu.upc.dama.users.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.oauth.OAuthService;

import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.users.model.Global;

public class SignUpWithFacebookAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONSUMER_KEY = "111615229025229";
	private static final String CONSUMER_KEY_SECRET = "9b4185549c7cbac57b960f7d42a2bafb";
	private static final String CALLBACK_URL = "http://www.fib.upc.edu/fib.html"; //TODO
	private Map<String, Object> session;
	private String authUrl;

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public String execute() throws Exception {
		
		//OAuthConfig config = new OAuthConfig(CONSUMER_KEY,CONSUMER_KEY_SECRET,CALLBACK_URL,null,"email");
		
		OAuthService service = new ServiceBuilder()
		.provider(FacebookApi.class)
		.apiKey(CONSUMER_KEY)
		.apiSecret(CONSUMER_KEY_SECRET)
		.callback(CALLBACK_URL)
		.scope("email user_about_me user_birthday user_location")
		.build();
		//TODO mirar com gestionar les permissions. Necessito els scopes: "email", "user_about_me", "user_birthday", "user_location"
		
		//Token requestToken = service.getRequestToken();
		
		authUrl = service.getAuthorizationUrl(null);
		
		session.put(Global.A_SERVICE, service);
		session.put(Global.A_REQUEST_TOKEN, null);
		
		return "success";
	}
	
	public String getAuthUrl() {
		return authUrl;
	}
}
