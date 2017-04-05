package org.xdi.oxd.badgemanager.ldap.models;

import org.apache.commons.codec.digest.DigestUtils;
import org.gluu.site.ldap.persistence.annotation.LdapAttribute;
import org.gluu.site.ldap.persistence.annotation.LdapEntry;
import org.gluu.site.ldap.persistence.annotation.LdapObjectClass;

import java.util.Date;

/**
 * Created by Arvind Tomar on 4/10/16.
 * Updated by Arvind Tomar on 7/10/16.
 */

@LdapEntry
@LdapObjectClass(values = {"top", "gluuBadgeInstance"})
public class BadgeInstances extends SimpleUser {

    @LdapAttribute(name = "gluuBadgeClassDN")
    String gluuBadgeClassDN;

    @LdapAttribute(name = "description")
    String description;

    @LdapAttribute(name = "gluuBadgeHash")
    String gluuBadgeHash;

    @LdapAttribute(name = "gluuBadgeIssueDate")
    Date gluuBadgeIssueDate;

    @LdapAttribute(name = "inum")
    String inum;

    @LdapAttribute(name = "gluuBadgeIssuer")
    String gluuBadgeIssuer;

    @LdapAttribute(name = "gluuBadgeInstanceOwner")
    String gluuBadgeInstanceOwner;

    @LdapAttribute(name = "gluuAssociatedOrganization")
    String organization;

    @LdapAttribute(name = "gluuBadgeExpiryDate")
    Date gluuBadgeExpiryDate;

    @LdapAttribute(name = "creationDate")
    Date creationDate = new Date();

    @LdapAttribute(name = "gluuStatus")
    boolean active = true;

    @LdapAttribute(name = "owner")

    String owner;


    Organizations gluuAssociatedOrganizationDetail = null;

    Person gluuBadgeIssuerDetial = null;

    Person ownerDetails = null;

    Badges badgeDetails = null;

    public BadgeInstances() {
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

    public Date getGluuBadgeIssueDate() {
        return gluuBadgeIssueDate;
    }

    public void setGluuBadgeIssueDate(Date gluuBadgeIssueDate) {
        this.gluuBadgeIssueDate = gluuBadgeIssueDate;
    }

    public String getInum() {
        return inum;
    }

    public void setInum(String inum) {
        this.inum = inum;
    }

    public String getGluuBadgeIssuer() {
        return gluuBadgeIssuer;
    }

    public void setGluuBadgeIssuer(String gluuBadgeIssuer) {
        this.gluuBadgeIssuer = gluuBadgeIssuer;
    }

    public String getGluuBadgeInstanceOwner() {
        return gluuBadgeInstanceOwner;
    }

    public void setGluuBadgeInstanceOwner(String gluuBadgeInstanceOwner) {
        this.gluuBadgeInstanceOwner = gluuBadgeInstanceOwner;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getGluuBadgeExpiryDate() {
        return gluuBadgeExpiryDate;
    }

    public void setGluuBadgeExpiryDate(Date gluuBadgeExpiryDate) {
        this.gluuBadgeExpiryDate = gluuBadgeExpiryDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String isOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGluuBadgeHash() {
        return gluuBadgeHash;
    }

    public void setGluuBadgeHash(String gluuBadgeHash) {
        this.gluuBadgeHash = DigestUtils.md5Hex(gluuBadgeHash);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getOwner() {
        return owner;
    }

    public Organizations getGluuAssociatedOrganizationDetail() {
        return gluuAssociatedOrganizationDetail;
    }

    public void setGluuAssociatedOrganizationDetail(Organizations gluuAssociatedOrganizationDetail) {
        this.gluuAssociatedOrganizationDetail = gluuAssociatedOrganizationDetail;
    }

    public Person getGluuBadgeIssuerDetial() {
        return gluuBadgeIssuerDetial;
    }

    public void setGluuBadgeIssuerDetial(Person gluuBadgeIssuerDetial) {
        this.gluuBadgeIssuerDetial = gluuBadgeIssuerDetial;
    }

    public Person getOwnerDetails() {
        return ownerDetails;
    }

    public void setOwnerDetails(Person ownerDetails) {
        this.ownerDetails = ownerDetails;
    }

    public Badges getBadgeDetails() {
        return badgeDetails;
    }

    public void setBadgeDetails(Badges badgeDetails) {
        this.badgeDetails = badgeDetails;
    }
}