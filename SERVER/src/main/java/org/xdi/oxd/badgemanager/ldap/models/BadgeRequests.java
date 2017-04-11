package org.xdi.oxd.badgemanager.ldap.models;

import org.gluu.site.ldap.persistence.annotation.LdapAttribute;
import org.gluu.site.ldap.persistence.annotation.LdapEntry;
import org.gluu.site.ldap.persistence.annotation.LdapObjectClass;

/**
 * Created by Arvind Tomar on 4/10/16.
 * Updated by Arvind Tomar on 7/10/16.
 */

@LdapEntry
@LdapObjectClass(values = {"top", "gluuBadgeRequest"})
public class BadgeRequests extends SimpleUser {

    @LdapAttribute(name = "inum")
    String inum;

//    @LdapAttribute(name = "gluuBadgeClassDN")
//    String gluuBadgeClassDN;

    @LdapAttribute(name = "masterBadgeId")
    String masterBadgeId;

    @LdapAttribute(name = "organization")
    String organization;

    @LdapAttribute(name = "status")
    String status;

    //@LdapAttribute(name = "gluuBadgeRequestStatus")
//    boolean requestStatus;

    @LdapAttribute(name = "gluuBadgeRequester")
    String gluuBadgeRequester;

    public BadgeRequests() {
    }

    public String getInum() {
        return inum;
    }

    public void setInum(String inum) {
        this.inum = inum;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getGluuBadgeRequester() {
        return gluuBadgeRequester;
    }

    public void setGluuBadgeRequester(String gluuBadgeRequester) {
        this.gluuBadgeRequester = gluuBadgeRequester;
    }

    public String getMasterBadgeId() {
        return masterBadgeId;
    }

    public void setMasterBadgeId(String masterBadgeId) {
        this.masterBadgeId = masterBadgeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}