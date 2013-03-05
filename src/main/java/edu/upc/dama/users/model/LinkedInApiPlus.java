package edu.upc.dama.users.model;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class LinkedInApiPlus extends DefaultApi10a
{
private static final String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authenticate?oauth_token=%s";

private static String permissions = "";

public static void addScopePermision(String permission)
{
if(!permissions.contains(permission))
{
if(permissions != "")
permissions += "+";
permissions += permission;
}
}

public static void clearScopePermisions()
{
permissions = "";
}

@Override
public String getAccessTokenEndpoint()
{
return "https://api.linkedin.com/uas/oauth/accessToken";
}

@Override
public String getRequestTokenEndpoint()
{
return "https://api.linkedin.com/uas/oauth/requestToken?scope=" + permissions;
}

@Override
public String getAuthorizationUrl(Token requestToken)
{
return String.format(AUTHORIZE_URL, requestToken.getToken());
}

}