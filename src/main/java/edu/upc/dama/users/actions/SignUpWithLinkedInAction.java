package edu.upc.dama.users.actions;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.opensymphony.xwork2.ActionSupport;

import edu.upc.dama.users.model.Global;
import edu.upc.dama.users.model.LinkedInApiPlus;

public class SignUpWithLinkedInAction extends ActionSupport implements
		SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONSUMER_KEY = "h6osmkyrceni";
	private static final String CONSUMER_KEY_SECRET = "zhZbJrJ0q19houhE";
	private static final String CALLBACK_URL = "Aqu’ hi he de posar l'URL de redirecci—"; //TODO
	private String authUrl;
	private Map<String, Object> session;
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	
	@Override
	public String execute() throws Exception {
		
		LinkedInApiPlus.addScopePermision("r_contactinfo");
		LinkedInApiPlus.addScopePermision("r_emailaddress");
		LinkedInApiPlus.addScopePermision("r_basicprofile");
		LinkedInApiPlus.addScopePermision("r_fullprofile");
		
	    OAuthService service = new ServiceBuilder()
        .provider(LinkedInApiPlus.class)
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
	
	public String getAuthUrl() {
		return authUrl;
	}
}
