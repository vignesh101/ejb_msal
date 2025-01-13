package com.ejb;

import com.microsoft.aad.msal4j.*;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Stateless
public class AuthenticationService {

    @EJB
    private MSALConfig msalConfig;

    private final Set<String> SCOPES = new HashSet<String>(){

        {
            add("User.Read");
            add("profile");
            add("email");
        }

    };

    public String getLoginUrl(String state) throws MalformedURLException {
        AuthorizationRequestUrlParameters parameters =
                AuthorizationRequestUrlParameters.builder(
                                msalConfig.getRedirectUri(),
                                SCOPES)
                        .state(state)
                        .responseMode(ResponseMode.QUERY)
                        .prompt(Prompt.SELECT_ACCOUNT)
                        .build();

        return msalConfig.getClient()
                .getAuthorizationRequestUrl(parameters)
                .toString();
    }

    public IAuthenticationResult getTokenByAuthCode(String authCode)
            throws ExecutionException, InterruptedException, MalformedURLException, URISyntaxException {
        AuthorizationCodeParameters parameters =
                AuthorizationCodeParameters.builder(
                                authCode,
                                new URI(msalConfig.getRedirectUri()))
                        .scopes(SCOPES)
                        .build();

        return msalConfig.getClient()
                .acquireToken(parameters)
                .get();
    }

}
