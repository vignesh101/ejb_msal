package com.ejb;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IConfidentialClientApplication;
import jakarta.ejb.PostActivate;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.net.MalformedURLException;

@Singleton
@Startup
public class MSALConfig {

    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String TENANT_ID = "";
    private static final String AUTHORITY = "https://login.microsoftonline.com/" + TENANT_ID + "/";
    private static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/";
    private IConfidentialClientApplication msalClient;


    public void init() {
        try {
            msalClient = ConfidentialClientApplication.builder(
                            CLIENT_ID,
                            ClientCredentialFactory.createFromSecret(CLIENT_SECRET))
                    .authority(AUTHORITY)
                    .build();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize MSAL client", e);
        }
    }

    public IConfidentialClientApplication getClient() {
        init();
        return msalClient;
    }

    public String getRedirectUri() {
        return REDIRECT_URI;
    }

}
