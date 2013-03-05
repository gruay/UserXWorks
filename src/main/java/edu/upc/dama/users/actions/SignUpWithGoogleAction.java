package edu.upc.dama.users.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.users.model.Global;

public class SignUpWithGoogleAction extends ActionSupport implements
		SessionAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONSUMER_KEY = "775851205685.apps.googleusercontent.com";
	private static final String CONSUMER_KEY_SECRET = "09seQt6sF-H7qHXFPomYUWyt";
	private static final String CALLBACK_URL = "www.fib.upc.edu"; //TODO
	private Map<String, Object> session;
	private String authUrl;
	
	public void validate() {
	}

	@Override
	public String execute() throws Exception {
		OAuthService service = new ServiceBuilder()
        .provider(GoogleApi.class)
        .apiKey(CONSUMER_KEY)
        .apiSecret(CONSUMER_KEY_SECRET)
        .callback(CALLBACK_URL)
        .scope("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile")
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
