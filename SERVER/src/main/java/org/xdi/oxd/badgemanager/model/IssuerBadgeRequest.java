package org.xdi.oxd.badgemanager.model;

/**
 * Created by Arvind Tomar on 10/4/17.
 */
public class IssuerBadgeRequest {
    String issuer;

    public IssuerBadgeRequest(String iss){
        this.issuer=iss;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
