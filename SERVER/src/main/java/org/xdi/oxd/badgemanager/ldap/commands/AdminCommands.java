package org.xdi.oxd.badgemanager.ldap.commands;

import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPException;
import org.gluu.site.ldap.persistence.LdapEntryManager;
import org.jboss.resteasy.spi.NotFoundException;
import org.xdi.oxd.badgemanager.config.DefaultConfig;
import org.xdi.oxd.badgemanager.ldap.models.*;

import java.util.*;

/**
 * Created by Arvind tomar on 17/10/16.
 */
public class AdminCommands {

    /**
     * getBadges created by person
     *
     * @param ldapEntryManager
     * @param person           person must have email
     * @return
     */
    public static HashMap<String, List<Badges>> getBadgeListForAdmin(LdapEntryManager ldapEntryManager, Person person) {

//        ou=organizations,ou=badges,o=@!420E.84FA.C49E.C0E2!0001!4EA2.AA20,o=gluu
        try {
            HashMap<String, List<Badges>> badgeHash = new HashMap<>();

            List<Organizations> organizationses = ldapEntryManager.findEntries("ou=organizations,ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu", Organizations.class, Filter.create("gluuManager=" + person.getEmail()));
            Badges badge = new Badges();
            badge.setDn("ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu");

            if (organizationses != null && organizationses.size() > 0) {
                for (Organizations organizations : organizationses) {
                    List<Badges> badges = ldapEntryManager.findEntries("ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu", Badges.class, Filter.create("gluuAssociatedOrganization=" + organizations.getDn()));
                    if (badges != null && badges.size() > 0) {
                        badgeHash.put(organizations.getDisplayName(), badges);
                    }
                }
                return badgeHash;
            } else return null;
        } catch (LDAPException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * getBadges created by person
     *
     * @param ldapEntryManager
     * @param person           person must have email
     * @return
     */
    public static HashMap<String, List<BadgeRequests>> getBadgesRequestsbyAdminEmail(LdapEntryManager ldapEntryManager, Person person) {

        try {
            HashMap<String, List<BadgeRequests>> badgeHash = new HashMap<>();

            List<Organizations> organizationses = ldapEntryManager.findEntries("ou=organizations,ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu", Organizations.class, Filter.create("gluuManager=" + person.getEmail()));
            BadgeRequests badge = new BadgeRequests();
            badge.setDn("ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu");

            if (organizationses != null && organizationses.size() > 0) {
                for (Organizations organizations : organizationses) {
                    List<BadgeRequests> badges = ldapEntryManager.findEntries("ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu", BadgeRequests.class, Filter.create("gluuAssociatedOrganization=" + organizations.getDn()));
                    if (badges != null && badges.size() > 0) {
                        for (BadgeRequests badgeRequests : badges) {
                            badgeRequests.setGluuAssociatedOrganizationDetails(OrganizationCommands.findOrganizationfromDN(ldapEntryManager, badgeRequests.getOrganization()));
                            badgeRequests.setGluuBadgeRequesterDetail(PersonCommands.findPersonfromDN(ldapEntryManager, badgeRequests.getGluuBadgeRequester()));
                            badgeRequests.setBadgeDetails(BadgeCommands.findBadgefromDN(ldapEntryManager, badgeRequests.getGluuBadgeClassDN()));
                        }
                        badgeHash.put(organizations.getDisplayName(), badges);
                    }
                }
                return badgeHash;
            } else
                throw new NotFoundException("Person with specified email does not exist in the system");
        } catch (LDAPException e) {
            e.printStackTrace();
            throw new NotFoundException("Person with specified email does not exist in the system");
        }
    }

    /**
     * @param ldapEntryManager
     * @param inum
     * @param expdate
     * @return
     */
    public static boolean acceptRequestByInum(LdapEntryManager ldapEntryManager, String inum, String expdate, String issuerEmail) {

        try {
            List<BadgeRequests> badgeRequestses = new ArrayList<>();
            badgeRequestses = ldapEntryManager.findEntries("ou=badgeRequests,ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu", BadgeRequests.class, Filter.create("inum=" + inum));
            if (badgeRequestses != null && badgeRequestses.size() > 0) {
                BadgeInstances badges = new BadgeInstances();
                BadgeRequests request = badgeRequestses.get(0);
                Person issuer = PersonCommands.getPersonByEmail(ldapEntryManager, issuerEmail);

                badges.setOwner(request.getGluuBadgeRequester());
                badges.setActive(true);
                badges.setGluuBadgeClassDN(request.getGluuBadgeClassDN());
                badges.setDescription(request.getDescription());
                badges.setGluuBadgeIssueDate(new Date());
                badges.setGluuBadgeIssuer(issuer.getDn());
                badges.setOrganization(request.getOrganization());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(new Long(expdate));
                badges.setGluuBadgeExpiryDate(calendar.getTime());

                try {
                    BadgeInstances badgeInstance = BadgeInstancesCommands.createNewBadgeInstances(ldapEntryManager, badges);
                    if (badgeInstance != null) {
                        ldapEntryManager.remove(request);
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

            } else return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Error in accepting badge request");
        }
    }


    /**
     * getBadges created by person
     *
     * @param ldapEntryManager
     * @param person           person must have email
     * @return
     */
    public static HashMap<String, List<BadgeInstances>> getBadgesInstancebyAdmin(LdapEntryManager ldapEntryManager, Person person) {

        try {
            HashMap<String, List<BadgeInstances>> badgeHash = new HashMap<>();
            person = PersonCommands.getPersonByEmail(ldapEntryManager, person.getEmail());
            List<Organizations> organizations = ldapEntryManager.findEntries("ou=organizations,ou=badges,o=" + DefaultConfig.config_organization + ",o=gluu", Organizations.class, Filter.create("(gluuManager=" + person.getEmail() + ")"));
            BadgeInstances badgeInstances = new BadgeInstances();
            badgeInstances.setDn("ou=badges,ou=people,o=" + DefaultConfig.config_organization + ",o=gluu");

            if (organizations != null && organizations.size() > 0) {
                for (Organizations organization : organizations) {
                    List<Filter> badgeInstanceFilter = new ArrayList<Filter>();

                    Filter organizationFilter = Filter.createEqualityFilter("gluuAssociatedOrganization", organization.getDn());
                    Filter issuerFilter = Filter.createEqualityFilter("gluuBadgeIssuer", person.getDn());

                    badgeInstanceFilter.add(organizationFilter);
                    badgeInstanceFilter.add(issuerFilter);

                    Filter filter = Filter.createANDFilter(badgeInstanceFilter);

                    List<BadgeInstances> badges = ldapEntryManager.findEntries(badgeInstances.getDn(), BadgeInstances.class, filter);

                    if (badges != null && badges.size() > 0) {
                        for (BadgeInstances instances : badges) {
                            instances.setOwnerDetails(PersonCommands.findPersonfromDN(ldapEntryManager, instances.getOwner()));
                            instances.setGluuBadgeIssuerDetial(PersonCommands.findPersonfromDN(ldapEntryManager, instances.getGluuBadgeIssuer()));
                            instances.setGluuAssociatedOrganizationDetail(OrganizationCommands.findOrganizationfromDN(ldapEntryManager, instances.getOrganization()));
                            instances.setBadgeDetails(BadgeCommands.findBadgefromDN(ldapEntryManager, instances.getGluuBadgeClassDN()));

                        }
                        badgeHash.put(organization.getDisplayName(), badges);
                    }
                }
                return badgeHash;
            } else throw new NotFoundException("No organizations found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException(e.getMessage());
        }

    }

}