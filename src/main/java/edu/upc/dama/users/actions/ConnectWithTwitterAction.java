package edu.upc.dama.users.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.users.model.Global;


public class ConnectWithTwitterAction extends ActionSupport implements SessionAware {

	
	/**
	 * Aquesta classe es crida quan es vol connectar amb Twitter.
	 * Diferents coses:
	 * A session hi guardaré el twitter i la requestToken
	 * L'user és l'usuari que es vol connectar amb Twitter.
	 * Que hauria de mirar que no hi estigués ja connectat?
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private static final String CONSUMER_KEY = "wbmxPXJAI6KwE0jt3VMH9A";
	private static final String CONSUMER_KEY_SECRET = "GWK5S2XSmhefM89mrn77A2oM5EiR4gsvgFES0ZkdBs";
	private static final String CALLBACK_URL = "Aquí hi he de posar l'URL de redirecció"; //TODO
	private Map<String, Object> session;
	private String authUrl;
	
	public void validate() {
	}

	@Override
	public String execute() throws Exception {
		OAuthService service = new ServiceBuilder()
        .provider(TwitterApi.class)
        .apiKey(CONSUMER_KEY)
        .apiSecret(CONSUMER_KEY_SECRET)
        .callback(CALLBACK_URL)
        .build();
		Token requestToken = service.getRequestToken();

		authUrl = service.getAuthorizationUrl(requestToken);
		
		session.put(Global.A_SERVICE, service);
		session.put(Global.A_REQUEST_TOKEN, requestToken);
		return "success";
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public String getAuthUrl() {
		return authUrl;
	}
}
