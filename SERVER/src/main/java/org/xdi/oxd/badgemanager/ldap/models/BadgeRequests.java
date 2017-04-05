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

    @LdapAttribute(name = "gluuBadgeClassDN")
    String gluuBadgeClassDN;

    @LdapAttribute(name = "description")
    String description;

    @LdapAttribute(name = "gluuAssociatedOrganization")
    String organization;

    @LdapAttribute(name = "gluuStatus")
    boolean active = false;

    @LdapAttribute(name = "gluuBadgeRequestStatus")
    boolean requestStatus;

    @LdapAttribute(name = "gluuBadgeRequester")
    String gluuBadgeRequester;

    Organizations gluuAssociatedOrganizationDetails = new Organizations();

    Person gluuBadgeRequesterDetail = new Person();

    Badges badgeDetails = new Badges();


    public BadgeRequests() {
    }

    public String getInum() {
        return inum;
    }

    public void setInum(String inum) {
        this.inum = inum;
    }

    public String getGluuBadgeClassDN() {
        return gluuBadgeClassDN;
    }

    public void setGluuBadgeClassDN(String gluuBadgeClassDN) {
        this.gluuBadgeClassDN = gluuBadgeClassDN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(boolean requestStatus) {
        this.requestStatus = requestStatus;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getGluuBadgeRequester() {
        return gluuBadgeRequester;
    }

    public void setGluuBadgeRequester(String gluuBadgeRequester) {
        this.gluuBadgeRequester = gluuBadgeRequester;
    }

    public Organizations getGluuAssociatedOrganizationDetails() {
        return gluuAssociatedOrganizationDetails;
    }

    public void setGluuAssociatedOrganizationDetails(Organizations gluuAssociatedOrganizationDetails) {
        this.gluuAssociatedOrganizationDetails = gluuAssociatedOrganizationDetails;
    }

    public Person getGluuBadgeRequesterDetail() {
        return gluuBadgeRequesterDetail;
    }

    public void setGluuBadgeRequesterDetail(Person gluuBadgeRequesterDetail) {
        this.gluuBadgeRequesterDetail = gluuBadgeRequesterDetail;
    }

    public Badges getBadgeDetails() {
        return badgeDetails;
    }

    public void setBadgeDetails(Badges badgeDetails) {
        this.badgeDetails = badgeDetails;
    }
}