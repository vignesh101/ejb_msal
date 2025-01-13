package com.ejb;

import com.microsoft.aad.msal4j.IAuthenticationResult;
import jakarta.ejb.Remove;
import jakarta.ejb.Stateful;


import java.util.Date;

@Stateful
public class UserSessionBean {

    private String accessToken;
    private String idToken;
    private Date tokenExpiry;

    public void setTokens(IAuthenticationResult authResult) {
        this.accessToken = authResult.accessToken();
        this.idToken = authResult.idToken();
        this.tokenExpiry = authResult.expiresOnDate();
    }

    public String getAccessToken() {
        if (isTokenExpired()) {
            return null;
        }
        return accessToken;
    }

    private boolean isTokenExpired() {
        return new Date().after(tokenExpiry);
    }

    @Remove
    public void logout() {
        this.accessToken = null;
        this.idToken = null;
        this.tokenExpiry = null;
    }

}
