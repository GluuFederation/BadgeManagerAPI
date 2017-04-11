package org.xdi.oxd.badgemanager.ldap.commands;

import com.unboundid.ldap.sdk.Filter;
import org.gluu.site.ldap.persistence.LdapEntryManager;
import org.xdi.oxd.badgemanager.config.DefaultConfig;
import org.xdi.oxd.badgemanager.ldap.models.BadgeRequests;
import org.xdi.oxd.badgemanager.ldap.service.InumService;

/**
 * Created by Arvind Tomar on 14/10/16.
 */
public class BadgeRequestCommands {
    //Crete new badge request according to organization and badge

    /**
     * Crete new badge request according to organization and badge
     *
     * @param ldapEntryManager ldapEntryManager
     * @param badgeRequest     pass Organization and badge class dn
     * @return
     * @throws Exception
     */
    public static BadgeRequests createBadgeRequest(LdapEntryManager ldapEntryManager, BadgeRequests badgeRequest) throws Exception {

        String inum = InumService.getInum(InumService.badgeRequestPrefix);
        badgeRequest.setDn("inum=" + inum + ",ou=badgeRequests,ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu");
        badgeRequest.setInum(inum);

        if (!(ldapEntryManager.contains("ou=badgeRequests,ou=badges,o=gluu", BadgeRequests.class, Filter.create("(inum=" + badgeRequest.getInum() + ")")))) {
            if (!ldapEntryManager.contains("ou=badgeRequests,ou=badges,o=gluu", BadgeRequests.class, Filter.create("(&(gluuBadgeRequester=" + badgeRequest.getGluuBadgeRequester() + "))"))) {
                badgeRequest.setStatus("Pending");
                ldapEntryManager.persist(badgeRequest);
                System.out.println("new badge request created");
                return badgeRequest;
            } else {
                throw new Exception("You have already requested for same badge");
            }
        } else {
            createBadgeRequest(ldapEntryManager, badgeRequest);
        }

        throw new Exception("There was problem creating a badge request");
    }

    /**
     * Deletes badge request by Inum
     *
     * @param ldapEntryManager ldapEntryManager
     * @param inum             inum of the badge request to be deleted
     * @return
     */
    public static boolean deleteBadgeRequestByInum(LdapEntryManager ldapEntryManager, String inum) {
        try {
            BadgeRequests badgeRequest = new BadgeRequests();
            badgeRequest.setDn("inum=" + inum + ",ou=badgeRequests,ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu");
            badgeRequest.setInum(inum);
            badgeRequest.setInum(inum);
            if (ldapEntryManager.contains(badgeRequest.getDn(), BadgeRequests.class, Filter.create("(inum=" + badgeRequest.getInum() + ")"))) {
                ldapEntryManager.remove(badgeRequest);
                System.out.println("Deleted entry ");
                return true;
            } else {

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}